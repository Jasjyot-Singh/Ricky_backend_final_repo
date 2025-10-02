-- Create drivers table
CREATE TABLE IF NOT EXISTS drivers (
    driver_id VARCHAR(50) PRIMARY KEY,
    driver_name VARCHAR(100) NOT NULL,
    driver_phone VARCHAR(20) NOT NULL UNIQUE,
    license_number VARCHAR(50) NOT NULL UNIQUE,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Create vehicles table  
CREATE TABLE IF NOT EXISTS vehicles (
    vehicle_id VARCHAR(50) PRIMARY KEY,
    plate_number VARCHAR(20) NOT NULL,
    driver_id VARCHAR(50) UNIQUE,
    FOREIGN KEY (driver_id) REFERENCES drivers(driver_id)
);

-- Create fare_info table
CREATE TABLE IF NOT EXISTS fare_info (
    fare_id VARCHAR(50) PRIMARY KEY,
    driver_id VARCHAR(50) NOT NULL,
    vehicle_id VARCHAR(50) NOT NULL,
    fare_date DATE,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    distance_km DOUBLE PRECISION,
    fare_amount DOUBLE PRECISION,
    FOREIGN KEY (driver_id) REFERENCES drivers(driver_id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id)
);

-- Insert sample data
INSERT INTO drivers (driver_id, driver_name, driver_phone, license_number, active)
VALUES ('DRIVER-1', 'Ravi Kumar', '9876543210', 'LIC-12345', TRUE);

INSERT INTO vehicles (vehicle_id, plate_number, driver_id)
VALUES ('VEH-1', 'DL01AB1234', 'DRIVER-1');

INSERT INTO fare_info (fare_id, driver_id, vehicle_id, fare_date, start_time, end_time, distance_km, fare_amount)
VALUES (
    'FARE-1',
    'DRIVER-1', 
    'VEH-1',
    '2025-09-23',
    '2025-09-23 08:00:00',
    '2025-09-23 08:30:00',
    12.5,
    250.0
);