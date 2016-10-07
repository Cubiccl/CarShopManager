/*!40014 SET  @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS,FOREIGN_KEY_CHECKS=0 */;

CREATE TABLE IF NOT EXISTS `customers` (`customerNumber` int(11) NOT NULL, `customerName` varchar(50) NOT NULL, `contactLastName` varchar(50) NOT NULL, `contactFirstName` varchar(50) NOT NULL, `phone` varchar(50) NOT NULL,`city` varchar(50) NOT NULL,`state` varchar(50) DEFAULT NULL, `postalCode` varchar(15) DEFAULT NULL, `country` varchar(50) NOT NULL, `salesRepEmployeeNumber` int(11) DEFAULT NULL, PRIMARY KEY (`customerNumber`), KEY `salesRepEmployeeNumber` (`salesRepEmployeeNumber`), CONSTRAINT `customers_fk_emplnumb` FOREIGN KEY (`salesRepEmployeeNumber`) REFERENCES `employees`(`employeeNumber`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `employees` (`employeeNumber` int(11) NOT NULL, `lastName` varchar(50) DEFAULT NULL, `firstName` varchar(50) DEFAULT NULL, `email` varchar(100) DEFAULT NULL, `officeCode` varchar(10) DEFAULT NULL, `reportsTo` int(11) DEFAULT NULL, `jobTitle` varchar(50) DEFAULT NULL, PRIMARY KEY (`employeeNumber`), CONSTRAINT `employes_fk_offcode` FOREIGN KEY (`officeCode`) REFERENCES `offices`(`officeCode`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `offices` (`officeCode` varchar(10) NOT NULL, `city` varchar(50) DEFAULT NULL, `phone` varchar(50) DEFAULT NULL, `addressLine` varchar(50) DEFAULT NULL, `state` varchar(50) DEFAULT NULL, `country` varchar(50) DEFAULT NULL, `postalCode` varchar(15) DEFAULT NULL, PRIMARY KEY (`officeCode`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `payments` (`customerNumber` int(11) NOT NULL, `checkNumber` varchar(50) NOT NULL, `paymentDate` date DEFAULT NULL, `amount` double DEFAULT NULL, PRIMARY KEY (`checkNumber`), CONSTRAINT `payments_fk_custnumb` FOREIGN KEY (`customerNumber`) REFERENCES `customers`(`customerNumber`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `orders` (`orderNumber` int(11) NOT NULL, `orderDate` date DEFAULT NULL, `requiredDate` date DEFAULT NULL, `shippedDate` date DEFAULT NULL, `status` varchar(15) NOT NULL, `comments` text NOT NULL, `customerNumber` int(11) NOT NULL, PRIMARY KEY (`orderNumber`), CONSTRAINT `orders_fk_custnumb` FOREIGN KEY (`customerNumber`) REFERENCES `customers`(`customerNumber`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `orderdetails` (`orderNumber` int(11) NOT NULL, `productCode` varchar(15) NOT NULL, `quantityOrdered` int(11) DEFAULT NULL, `priceEach` double DEFAULT NULL, `orderLineNumber` smallint(15) DEFAULT NULL, PRIMARY KEY (`orderNumber`, `productCode`), CONSTRAINT `orderdetails_fk_prodcode` FOREIGN KEY (`productCode`) REFERENCES `products`(`productCode`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `products` (`productCode` varchar(15) NOT NULL, `productName` varchar(70) DEFAULT NULL, `productLine` varchar(50) DEFAULT NULL, `photo` blob DEFAULT NULL, `productVendor` varchar(50) DEFAULT NULL, `productDescription` text DEFAULT NULL, `quantityInStock` smallint(50) DEFAULT NULL, `buyPrice` double DEFAULT NULL, `MSRP` double DEFAULT NULL, PRIMARY KEY (`productCode`), CONSTRAINT `products_fk_prodline` FOREIGN KEY (`productLine`) REFERENCES `productlines`(`productLine`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `productlines` (`productLine` varchar(50) NOT NULL, `textDescription` varchar(4000) DEFAULT NULL, PRIMARY KEY (`productLine`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;