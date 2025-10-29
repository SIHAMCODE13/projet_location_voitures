/**
 * 
 */
/**
 * 
 */
module test {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens test to javafx.base, javafx.fxml;
    requires ucanaccess;
	requires javafx.graphics;
	requires javafx.base;
    
        exports test;
    }

    
    
    
    
    
    
