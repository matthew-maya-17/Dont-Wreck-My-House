# Module05-Mastery-Project-Dont-Wreck-My-House
## Introduction
Using everything you've learned in Java Fundamentals, design, implement, and test an application that allows a user to reserve accommodations for a guest with a host. Don't Wreck My House is similar to Airbnb. A guest chooses a place to stay for a specific date range. If the host location is available, it may be reserved by the guest. Reserved locations are not available to other guests during reserved dates.

## High-Level Requirements
The application user is an accommodation administrator. They pair guests to hosts to make reservations.

- The administrator may view existing reservations for a host.
- The administrator may create a reservation for a guest with a host.
- The administrator may edit existing reservations.
- The administrator may cancel a future reservation.

## Glossary
### Guest
A customer. Someone who wants to book a place to stay. Guest data is provided via a zip download.

### Host
The accommodation provider. Someone who has a property to rent per night. Host data is provided.

### Location
A rental property. In Don't Wreck My House, Location and Host are combined. The application enforces a limit on one Location per Host, so we can think of a Host and Location as a single thing.

### Reservation
One or more days where a Guest has exclusive access to a Location (or Host). Reservation data is provided.

### Administrator
The application user. Guests and Hosts don't book their own

## Requirements
There are four required scenarios.

### View Reservations for Host
Display all reservations for a host.

- The user may enter a value that uniquely identifies a host or they can search for a host and pick one out of a list.
- If the host is not found, display a message.
- If the host has no reservations, display a message.
- Show all reservations for that host.
- Show useful information for each reservation: the guest, dates, totals, etc.
- Sort reservations in a meaningful way.

### Make a Reservation
Books accommodations for a guest at a host.

- The user may enter a value that uniquely identifies a guest or they can search for a guest and pick one out of a list.
- The user may enter a value that uniquely identifies a host or they can search for a host and pick one out of a list.
- Show all future reservations for that host so the administrator can choose available dates.
- Enter a start and end date for the reservation.
- Calculate the total, display a summary, and ask the user to confirm. The reservation total is based on the host's standard rate and weekend rate. For each day in the reservation, determine if it is a weekday or a weekend. If it's a weekday, the standard rate applies. If it's a weekend, the weekend rate applies.
- On confirmation, save the reservation.

### Validation
- Guest, host, and start and end dates are required.
- The guest and host must already exist in the "database". Guests and hosts cannot be created.
- The start date must come before the end date.
- The reservation may never overlap existing reservation dates.
- The start date must be in the future.

### Edit a Reservation
Edits an existing reservation.

- Find a reservation.
- Start and end date can be edited. No other data can be edited.
- Recalculate the total, display a summary, and ask the user to confirm.
### Validation
- Guest, host, and start and end dates are required.
- The guest and host must already exist in the "database". Guests and hosts cannot be created.
- The start date must come before the end date.
- The reservation may never overlap existing reservation dates.
### Cancel a Reservation
Cancel a future reservation.
- Find a reservation.
- Only future reservations are shown.
- On success, display a message.
### Validation
- You cannot cancel a reservation that's in the past.

## Technical Requirements
- Must be a Maven project.
- Spring dependency injection configured with either XML or annotations.
- All financial math must use BigDecimal.
- Dates must be LocalDate, never strings.
- All file data must be represented in models in the application.
- Reservation identifiers are unique per host, not unique across the entire application. Effectively, the combination of a reservation identifier and a host identifier is required to uniquely identify a reservation.

## File Format
The data file format is bad, but it's required. You may not change the file formats or the file delimiters. You must use the files provided. Guests are stored in their own comma-delimited file, guests.csv, with a header row. Hosts are stored in their own comma-delimited file, hosts.csv, with a header row.

Reservations are stored across many files, one for each host. A host reservation file name has the format: {host-identifier}.csv.

### Examples
Reservations for host c6567347-6c57-4658-a2c7-50040eeeb80f are stored in c6567347-6c57-4658-a2c7-50040eeeb80f.csv

Reservations for host 54508cfa-4f67-4de8-9437-6f27d65b0af0 are stored in 54508cfa-4f67-4de8-9437-6f27d65b0af0.csv

Reservations for host 6a3ef437-289c-40a9-b88a-dd70fad3fdbc are stored in 6a3ef437-289c-40a9-b88a-dd70fad3fdbc.csv

Testing
All data components must be thoroughly tested. Tests must always run successfully regardless of the data starting point, so it's important to establish known good state. Never test with "production" data.

All domain components must be thoroughly tested using test doubles. Do not use file repositories for domain testing.

User interface testing is not required.

Deliverables
- A schedule of concrete tasks. Tasks should contain time estimates and should never be more than 4 hours.
- Class diagram (informal is okay)
- Sequence diagrams or flow charts (optional, but encouraged)
- A Java Maven project that contains everything needed to run without error
- Test suite
