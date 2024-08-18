-- Create the 'company' table
CREATE TABLE company
(
    company_name     VARCHAR(100),
    company_earnings DOUBLE PRECISION
);

-- Create the 'employ' table
CREATE TABLE employ
(
    employ_id        SERIAL,
    employ_name      VARCHAR(100),
    company_name     VARCHAR(100),
    building_address CHAR(100)
);

-- Create the 'building' table
CREATE TABLE building
(
    building_name               CHAR(100),
    company_name                VARCHAR(100),
    building_address            CHAR(100),
    building_floors             INT,
    building_flats              INT,
    building_square_common_part INT
);

-- Create the 'flat_info' table
CREATE TABLE flat_info
(
    building_name     CHAR(100),
    flat_num          INT,
    flat_floor        INT,
    flat_elevator     BOOLEAN,
    flat_square       FLOAT,
    flat_people       INT,
    flat_kids         INT,
    flat_pet          BOOLEAN,
    flat_pet_elevator BOOLEAN
);

-- Create the 'people' table
CREATE TABLE people
(
    name     VARCHAR(100),
    age      INT,
    flat_num INT,
    address  CHAR(100)

);

------------------
-- Add primary keys to the tables

-- 'company' table
ALTER TABLE company
    ADD PRIMARY KEY (company_name);

-- 'employ' table
ALTER TABLE employ
    ADD PRIMARY KEY (employ_id);

-- 'building' table
ALTER TABLE building
    ADD PRIMARY KEY (building_name);

-- 'flat_info' table
ALTER TABLE flat_info
    ADD PRIMARY KEY (flat_num);

-- Add foreign keys to the tables

-- 'employ' table
ALTER TABLE employ
    ADD FOREIGN KEY (company_name) REFERENCES company (company_name);
ALTER TABLE employ
    ADD FOREIGN KEY (building_address) REFERENCES building (building_address);

-- Add foreign key (company_name) reference to the 'employ' table
ALTER TABLE employ
    ADD FOREIGN KEY (company_name) REFERENCES company (company_name);


-- 'building' table
ALTER TABLE building
    ADD FOREIGN KEY (company_name) REFERENCES company (company_name);

-- 'flat_info' table
ALTER TABLE flat_info
    ADD FOREIGN KEY (building_name) REFERENCES building (building_name);

-- 'people' table
ALTER TABLE people
    ADD FOREIGN KEY (flat_num) REFERENCES flat_info (flat_num);
-- Add a unique constraint to the 'building_address' column in the 'building' table
ALTER TABLE building
    ADD CONSTRAINT unique_building_address UNIQUE (building_address);

-- Table to store information about fees
CREATE TABLE fees
(
    fee_id          serial PRIMARY KEY,
    fee_name        varchar(100)     NOT NULL,
    fee_description text,
    fee_amount      double precision NOT NULL,
    fee_due_date    date             NOT NULL,
    fee_type        varchar(50)      NOT NULL CHECK (fee_type IN ('Building', 'Flat')),
    building_id     integer, -- References the building if it's a building-wide fee
    flat_id         integer, -- References the flat if it's a flat-specific fee
    CONSTRAINT fk_building_id FOREIGN KEY (building_id) REFERENCES building (building_id) ON DELETE CASCADE,
    CONSTRAINT fk_flat_id FOREIGN KEY (flat_id) REFERENCES flat_info (flat_id) ON DELETE CASCADE,
    CONSTRAINT ck_fee_unique_building_flat CHECK (
        (building_id IS NOT NULL AND flat_id IS NULL) OR
        (building_id IS NULL AND flat_id IS NOT NULL)
        )
);

-- Table to store payments made by residents
CREATE TABLE payments
(
    payment_id     serial PRIMARY KEY,
    resident_id    integer          NOT NULL, -- References the resident making the payment
    fee_id         integer          NOT NULL, -- References the fee being paid
    payment_date   date             NOT NULL,
    payment_amount double precision NOT NULL,
    payment_method varchar(50),               -- Payment method like 'Cash', 'Credit Card', etc.
    CONSTRAINT fk_resident_id FOREIGN KEY (resident_id) REFERENCES residents (resident_id) ON DELETE CASCADE,
    CONSTRAINT fk_fee_id FOREIGN KEY (fee_id) REFERENCES fees (fee_id) ON DELETE CASCADE
);
