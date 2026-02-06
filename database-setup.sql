-- ===========================
-- DROP EXISTING TABLES (in dependency-safe order)
-- ===========================
DROP TABLE IF EXISTS sos_alerts CASCADE;
DROP TABLE IF EXISTS ride_fare_data CASCADE;
DROP TABLE IF EXISTS fare_rates CASCADE;
DROP TABLE IF EXISTS vehicles CASCADE;
DROP TABLE IF EXISTS drivers CASCADE;
DROP TABLE IF EXISTS daily_driver_score_data CASCADE;
DROP TABLE IF EXISTS mldata CASCADE;
DROP TABLE IF EXISTS acceloremeter_data_class CASCADE;

CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- ===========================
-- CREATE TABLES
-- ===========================

-- Create drivers table
CREATE TABLE drivers (
    driver_id VARCHAR(50) PRIMARY KEY,
    driver_name VARCHAR(100) NOT NULL,
    driver_phone VARCHAR(20) NOT NULL UNIQUE,
    license_number VARCHAR(50) NOT NULL UNIQUE,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Create vehicles table
CREATE TABLE vehicles (
    vehicle_id VARCHAR(50) PRIMARY KEY,
    plate_number VARCHAR(20) NOT NULL,
    driver_id VARCHAR(50) UNIQUE,
    FOREIGN KEY (driver_id) REFERENCES drivers(driver_id)
);

-- Create fare_rates table (RTO manages this - one rate for all)
CREATE TABLE fare_rates (
    id BIGSERIAL PRIMARY KEY,
    rate DECIMAL(10,2) NOT NULL,
    updated_at TIMESTAMP,
    updated_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT NOW(),
    active BOOLEAN DEFAULT TRUE
);

-- Create ride_fare_data table (individual ride data from autometers)
CREATE TABLE ride_fare_data (

    -- 🔑 Database-generated primary key
    id BIGSERIAL PRIMARY KEY,

    -- 🧾 Business reference (from Pi)
    ride_id VARCHAR(64) NOT NULL,

    driver_id VARCHAR(50) NOT NULL,
    passenger_id VARCHAR(50),

    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,

    start_latitude DOUBLE PRECISION,
    start_longitude DOUBLE PRECISION,
    end_latitude DOUBLE PRECISION,
    end_longitude DOUBLE PRECISION,

    distance_km DOUBLE PRECISION,
    fare_amount DOUBLE PRECISION,
    fare_rate DOUBLE PRECISION,

    received_at TIMESTAMP DEFAULT NOW(),

    CONSTRAINT fk_ride_driver
        FOREIGN KEY (driver_id)
        REFERENCES drivers(driver_id)
        ON DELETE RESTRICT
);



-- Create sos_alerts table (for persistent SOS data)
CREATE TABLE sos_alerts (
    id VARCHAR(50) PRIMARY KEY,

    driver_id VARCHAR(50) NOT NULL,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,

    timestamp TIMESTAMP DEFAULT NOW(),
    acknowledged BOOLEAN DEFAULT FALSE,
    status VARCHAR(20) DEFAULT 'ACTIVE',

    CONSTRAINT fk_sos_driver
        FOREIGN KEY (driver_id)
        REFERENCES drivers(driver_id)
        ON DELETE CASCADE
);


CREATE TABLE acceloremeter_data_class (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    driver_id VARCHAR(50) NOT NULL,
    timestamp TIMESTAMP,

    lat DOUBLE PRECISION,
    lon DOUBLE PRECISION,

    accel_x DOUBLE PRECISION,
    accel_y DOUBLE PRECISION,
    accel_z DOUBLE PRECISION,

    gyro_x DOUBLE PRECISION,
    gyro_y DOUBLE PRECISION,
    gyro_z DOUBLE PRECISION,

    CONSTRAINT fk_accel_driver
        FOREIGN KEY (driver_id)
        REFERENCES drivers(driver_id)
        ON DELETE CASCADE
);


CREATE TABLE mldata (
    id SERIAL PRIMARY KEY,
    lat DOUBLE PRECISION NOT NULL,
    lon DOUBLE PRECISION NOT NULL,
    pothole_label INT NOT NULL,
    confidence DOUBLE PRECISION NOT NULL
);



CREATE TABLE daily_driver_score_data (
    day_wise_id SERIAL PRIMARY KEY,
    driver_id VARCHAR(50) NOT NULL,
    date DATE NOT NULL,
    daily_driver_score DOUBLE PRECISION NOT NULL,
    CONSTRAINT fk_driver FOREIGN KEY (driver_id) REFERENCES drivers(driver_id) ON DELETE CASCADE
);



-- Sample accelerometer data for driver "driver_123"

INSERT INTO acceloremeter_data_class
(id, driver_id, timestamp, lat, lon, accel_x, accel_y, accel_z, gyro_x, gyro_y, gyro_z)
VALUES
(gen_random_uuid(), 'driver_123', '2025-10-11 12:00:00', 12.9716, 77.5946, 0.01, 0.02, 0.98, 0.001, 0.002, 0.003),
(gen_random_uuid(), 'driver_123', '2025-10-11 12:00:01', 12.9717, 77.5947, 0.03, 0.01, 0.97, 0.002, 0.001, 0.004),
(gen_random_uuid(), 'driver_123', '2025-10-11 12:00:02', 12.9718, 77.5948, 0.02, 0.03, 0.99, 0.003, 0.002, 0.001),
(gen_random_uuid(), 'driver_123', '2025-10-11 12:00:03', 12.9719, 77.5949, 0.01, 0.04, 0.96, 0.004, 0.003, 0.002),
(gen_random_uuid(), 'driver_123', '2025-10-11 12:00:04', 12.9720, 77.5950, 0.05, 0.02, 0.95, 0.005, 0.004, 0.003);


-- ===========================
-- INSERT SAMPLE DATA
-- ===========================


-- ===========================
-- INSERT SAMPLE DRIVERS (3)
-- ===========================
INSERT INTO drivers (driver_id, driver_name, driver_phone, license_number, active) VALUES
('DRIVER-1', 'Ravi Kumar', '9876543210', 'LIC-12345', TRUE),
('DRIVER-2', 'Amit Sharma', '9876543211', 'LIC-12346', TRUE),
('DRIVER-3', 'Suresh Patil', '9876543212', 'LIC-12347', TRUE);

-- ===========================
-- INSERT SAMPLE VEHICLES (3)
-- ===========================
INSERT INTO vehicles (vehicle_id, plate_number, driver_id) VALUES
('VEH-1', 'DL01AB1234', 'DRIVER-1'),
('VEH-2', 'MH12CD5678', 'DRIVER-2'),
('VEH-3', 'KA05EF9012', 'DRIVER-3');

-- ===========================
-- INSERT FARE RATES (3)
-- ===========================
INSERT INTO fare_rates (rate, updated_by, active) VALUES
(12.00, 'SYSTEM', TRUE),
(13.50, 'ADMIN', TRUE),
(11.00, 'RTO', FALSE);

-- ===========================
-- INSERT RIDE FARE DATA (3)
-- ===========================
INSERT INTO ride_fare_data
(id, ride_id, driver_id, passenger_id, start_time, end_time,
 start_latitude, start_longitude, end_latitude, end_longitude,
 distance_km, fare_amount, fare_rate)
VALUES
('RIDE-DATA-1', 'RIDE-1', 'DRIVER-1', 'PASS-1',
 '2025-10-11 10:00:00', '2025-10-11 10:25:00',
 12.9716, 77.5946, 12.9816, 77.6046,
 5.0, 60.0, 12.0),

('RIDE-DATA-2', 'RIDE-2', 'DRIVER-2', 'PASS-2',
 '2025-10-11 11:00:00', '2025-10-11 11:40:00',
 19.0760, 72.8777, 19.0860, 72.8877,
 7.0, 94.5, 13.5),

('RIDE-DATA-3', 'RIDE-3', 'DRIVER-3', 'PASS-3',
 '2025-10-11 12:00:00', '2025-10-11 12:20:00',
 13.0827, 80.2707, 13.0927, 80.2807,
 4.0, 44.0, 11.0);

-- ===========================
-- INSERT SOS ALERTS (3)
-- ===========================
INSERT INTO sos_alerts (id, driver_id, latitude, longitude, acknowledged, status) VALUES
('SOS-1', 'DRIVER-1', 12.9716, 77.5946, FALSE, 'ACTIVE'),
('SOS-2', 'DRIVER-2', 19.0760, 72.8777, TRUE, 'RESOLVED'),
('SOS-3', 'DRIVER-3', 13.0827, 80.2707, FALSE, 'ACTIVE');

-- ===========================
-- INSERT ACCELEROMETER DATA (3)
-- ===========================
INSERT INTO acceloremeter_data_class
(id, driver_id, timestamp, lat, lon,
 accel_x, accel_y, accel_z,
 gyro_x, gyro_y, gyro_z)
VALUES
(gen_random_uuid(), 'DRIVER-1', '2025-10-11 12:00:00', 12.9716, 77.5946, 0.01, 0.02, 0.98, 0.001, 0.002, 0.003),
(gen_random_uuid(), 'DRIVER-2', '2025-10-11 12:00:01', 19.0760, 72.8777, 0.03, 0.01, 0.97, 0.002, 0.001, 0.004),
(gen_random_uuid(), 'DRIVER-3', '2025-10-11 12:00:02', 13.0827, 80.2707, 0.02, 0.03, 0.99, 0.003, 0.002, 0.001);

-- ===========================
-- INSERT ML DATA (3)
-- ===========================
INSERT INTO mldata (lat, lon, pothole_label, confidence) VALUES
(12.9716, 77.5946, 1, 0.92),
(19.0760, 72.8777, 0, 0.87),
(13.0827, 80.2707, 1, 0.95);

-- ===========================
-- INSERT DAILY DRIVER SCORE DATA (3)
-- ===========================
INSERT INTO daily_driver_score_data (driver_id, date, daily_driver_score) VALUES
('DRIVER-1', '2025-10-10', 85.5),
('DRIVER-2', '2025-10-10', 78.0),
('DRIVER-3', '2025-10-10', 90.2);


