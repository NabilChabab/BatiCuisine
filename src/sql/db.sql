-- Table for Clients
CREATE TABLE Clients (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         address VARCHAR(255),
                         phone VARCHAR(20),
                         isProfessional BOOLEAN NOT NULL
);

-- Enumeration for Project Status
CREATE TYPE projectStatus AS ENUM ('INPROGRESS', 'COMPLETED', 'CANCELLED');

-- Table for Projects
CREATE TABLE Projects (
                          id SERIAL PRIMARY KEY,
                          projectName VARCHAR(255) NOT NULL,
                          profitMargin DOUBLE PRECISION,
                          totalCost DOUBLE PRECISION,
                          status projectStatus,
                          client_id INT,
                          FOREIGN KEY (client_id) REFERENCES Clients(id) ON DELETE CASCADE
);

-- Table for Components
CREATE TABLE Components (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            componentType VARCHAR(255),
                            vatRate DOUBLE PRECISION
);

-- Table for Materials
CREATE TABLE Materials (
                           id SERIAL PRIMARY KEY,
                           component_id INT,
                           unitCost DOUBLE PRECISION,
                           quantity DOUBLE PRECISION,
                           transportCost DOUBLE PRECISION,
                           qualityCoefficient DOUBLE PRECISION,
                           FOREIGN KEY (component_id) REFERENCES Components(id) ON DELETE CASCADE
);

-- Table for Labor
CREATE TABLE Labor (
                       id SERIAL PRIMARY KEY,
                       component_id INT,
                       hourlyRate DOUBLE PRECISION,
                       workHours DOUBLE PRECISION,
                       workerProductivity DOUBLE PRECISION,
                       FOREIGN KEY (component_id) REFERENCES Components(id) ON DELETE CASCADE
);

-- Table for Quotes
CREATE TABLE Quotes (
                        id SERIAL PRIMARY KEY,
                        estimatedAmount DOUBLE PRECISION,
                        issueDate DATE,
                        isAccepted BOOLEAN,
                        project_id INT,
                        FOREIGN KEY (project_id) REFERENCES Projects(id) ON DELETE CASCADE
);