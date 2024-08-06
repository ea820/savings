-- Create the table if it does not exist
CREATE TABLE IF NOT EXISTS savings (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       custno VARCHAR(20),
    custname VARCHAR(20),
    cdep DOUBLE,
    nyears INT,
    savtype VARCHAR(20)
    );

-- Insert initial data
INSERT INTO savings (custno, custname, cdep, nyears, savtype) VALUES
                                                                  ('115', 'Jasper Diaz', 15000.0, 5, 'Savings-Deluxe'),
                                                                  ('112', 'Zanip Mendez', 5000.0, 2, 'Savings-Deluxe'),
                                                                  ('113', 'Geronima Esper', 6000.0, 5, 'Savings-Regular');
