# ***Don’t Wreck My House Project Plan***

## Main (Estimated Time To Finish: ~54 Hours &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Actual Time Taken: )  
### Models - Expected: 03/04/2025 (Estimated Time To Finish: 4 Hours &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Actual Time Taken: )
1. Guest
   - Fields:
       - Guest_id [int]
       - First_name [String]
       - Last_name [String]
       - Email [String]
       - phone [String]
       - State [String]
   - Constructor
   - Methods
       - Getters
       - equals
       - hashCode
2. Host
   - Fields
       - Id [String]
       - Last_name [String]
       - Email [String]
       - Phone [String]
       - Address [String]
       - City [String]
       - State [String]
       - Postal_code [Int]
       - Standard rate [Big Decimal]
       - Weekend rate [Big Decimal]
  - Constructor
  -  Methods
      - Getters
      - equals
      - hashCode  
3. Reservation
  - Fields
      - id [int]
      - start_date [LocalDate]
      - end_date [LocalDate]
      - guest_id [int]
      - guest [Guest]
      - host [Host]
      - total [Big Decimal]
  - Constructor
  - Methods
      - Getters
      - Setters
        
### Data Access Layer - Expected: 03/04/2025 (Estimated Time To Finish: 8 Hours split int 2 4 hour sections &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Actual Time Taken: ) 
1. Data Exception Class: Extends Exception
   - public DataException(String message) {
      super(message);
     }

   - public DataException(Throwable cause) {
      super(cause);
     }
     
   - public DataException(String message, Throwable cause) {
      super(message, cause);
     }
2. GuestRepository Interface
READ
   - List<String> findAll
   - List<String> findByEmail
   - List<String> findByLastName
3. GuestFileRepository Class  
   - List<String> findAll
   - List<String> findByEmail
   - List<String> findByLastName
4. HostRepository Interface
READ
   - List<String> findAll 
   - List<String> findByEmail
   - List<String> findByLastName
4. HostFileRepository Class
READ
   - List<String> findAll
   - List<String> findByEmail
   - List<String> findByLastName
5. ReservationRespository Interface
CREATE
   - Reservation addReservation (Reservation reservation)
      - Calculates the total for the reservation using LocalDate based on Standard rate (Sunday - Thursday) and Weekend Rate (Friday - Saturday)
      - Adds a reservation as long as the dates don’t overlap with other requirements
      - The start date must come before the end date.
      - The start date must be in the future.
      - Guest, host, and start and end dates are required.
      - The guest and host must already exist in the "database". Guests and hosts cannot be created
READ
   - List<Host> findByEmail ( String Email )
   - List<Host> findByGuestId ( int id )
   - List<Host> findByLastName ( String lastName )
UPDATE 
   - Reservation updateReservation ( Reservation reservation )
      - Looks for a reservation using one of the above READ methods
      - Start and end date can be edited. No other data can be edited.
      - This means the total will need to be recalculated (Can make the calculation its own method to separate concerns)
DELETE
   - Reservation deleteReservation (Reservation reservation)
   - Looks for a reservation using one of the above READ methods
   - Will only display future reservations
6. ReservationFileRepository Class
CREATE
   - Reservation addReservation (Reservation reservation)
      - Calculates the total for the reservation using LocalDate based on Standard rate (Sunday - Thursday) and Weekend Rate (Friday - Saturday)
      - Adds a reservation as long as the dates don’t overlap with other requirements
      - The start date must come before the end date.
      - The start date must be in the future.
      - Guest, host, and start and end dates are required.
      - The guest and host must already exist in the "database". Guests and hosts cannot be created
READ
   - List<Host> findByEmail ( String Email )
   - List<Host> findByGuestId ( int id )
   - List<Host> findByLastName ( String lastName )
UPDATE 
   - Reservation updateReservation ( Reservation reservation )
        Looks for a reservation using one of the above READ methods
     Start and end date can be edited. No other data can be edited.
     This means the total will need to be recalculated (Can make the calculation its own method to separate concerns)
DELETE
   - Reservation deleteReservation (Reservation reservation)
   - Looks for a reservation using one of the above READ methods
   - Will only display future reservations

### Domain/Service Layer - Expected: 03/05/2025 (Estimated Time To Finish: 8 hours split into 2 4 hour sections &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Actual Time Taken: ) 
1. ReservationResult
   - private final ArrayList<Reservation> reservations = new ArrayList<>();
   - private Reservation reservation;

   - public Panel getReservation(){
      return reservation;
     }

   - public void setReservation(Reservation reservation){
      this.reservation = reservation;
     }

   - public boolean isSuccess() {
      return reservations.size() == 0;
     }

   - public List<Reservation> getReservation(){
      return reservations;
     }

   - public void addMessage(Reservation reservation){
      reservations.add(reservation);
     }
2. GuestService 
   - Include all view methods used
3. HostService
   - Include all view methods used
4. ReservationService
   - Include all view methods used
   - Add + validations
   - Update + validations checked
   - Delete + validations checked

### UI Layer - Expected: 03/06/2025 (Estimated Time To Finish: 10 hours &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Actual Time Taken: )  
1. MainMenuOption (ENUM)
2. ConsoleIO (If I wanna make a separate class or integrate directly into View)
3. View: UI Validation  (Expecting the view layer will be broken up into three sections (I/O methods,View + Add, and  Update + Delete) will each take about 4 hours with their corresponding testing)
4. Controller: 
   - Menu
   - View Reservations
   - Add Reservations
   - Update Reservations
   - Delete Reservations

### App.Java - Expected: 03/06/2025 (Estimated Time To Finish: 2 hours &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Actual Time Taken: )  
- Dependency injections + setting up the application to run with instance method call (run).
- Resource File: dependency-injection.xml

### FINAL DEBUGGING + RESEARCH - Expected: 03/06/2025 - 03/07/2025 (Estimated Time To Finish: 8 hours &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Actual Time Taken: ) 
- Solve any unresolved final issues

## Tests - Expected: 03/05/2025 - 03/06/2025
### Data Access Layer Tests (Estimated Time To Finish: 7 hours &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Actual Time Taken: )  
1. GuestFileRepositoryTest
2. GuestRepositoryDouble
3. HostFileRepositoryTest
4. HostRepositoryDouble
5. RegistrationFileRepositoryTest
6. RegistrationRepositoryDouble
   
### Domain/Service Layer Tests - Expected: 03/06/2025 - 03/7/2025 (Estimated Time To Finish: 7 hours &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Actual Time Taken: ) 
1. GuestServiceTest
2. HostServiceTest
3. RegistrationServiceTest
