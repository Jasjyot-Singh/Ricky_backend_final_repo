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
    id VARCHAR(50) PRIMARY KEY,
    ride_id VARCHAR(50),
    driver_id VARCHAR(50),
    passenger_id VARCHAR(50),
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    start_latitude DOUBLE PRECISION,
    start_longitude DOUBLE PRECISION,
    end_latitude DOUBLE PRECISION,
    end_longitude DOUBLE PRECISION,
    distance_km DOUBLE PRECISION,
    fare_amount DOUBLE PRECISION,
    fare_rate DOUBLE PRECISION,
    received_at TIMESTAMP DEFAULT NOW()
);

-- Create sos_alerts table (for persistent SOS data)
CREATE TABLE sos_alerts (
    id VARCHAR(50) PRIMARY KEY,
    driver_id VARCHAR(50),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    timestamp TIMESTAMP DEFAULT NOW(),
    acknowledged BOOLEAN DEFAULT FALSE,
    status VARCHAR(20) DEFAULT 'ACTIVE'
);

CREATE TABLE acceloremeter_data_class (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    driver_id VARCHAR(100),
    timestamp TIMESTAMP,
    lat DOUBLE PRECISION,
    lon DOUBLE PRECISION,
    accel_x DOUBLE PRECISION,
    accel_y DOUBLE PRECISION,
    accel_z DOUBLE PRECISION,
    gyro_x DOUBLE PRECISION,
    gyro_y DOUBLE PRECISION,
    gyro_z DOUBLE PRECISION
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

CREATE EXTENSION IF NOT EXISTS pgcrypto;

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
INSERT INTO drivers (driver_id, driver_name, driver_phone, license_number, active)
VALUES ('DRIVER-1', 'Ravi Kumar', '9876543210', 'LIC-12345', TRUE);

INSERT INTO vehicles (vehicle_id, plate_number, driver_id)
VALUES ('VEH-1', 'DL01AB1234', 'DRIVER-1');

INSERT INTO fare_rates (rate, updated_by, active)
VALUES (12.0, 'SYSTEM', TRUE);
