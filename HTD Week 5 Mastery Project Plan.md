# ***Don’t Wreck My House Project Plan***

## Main
### Models
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
### Data Access Layer
### Domain/Service Layer
### UI Layer

## Tests
