# Computer Programming 2 — Real Estate Assignment

Pair assignment by **Olivia Espiñeira** and **Lara Iglesias**

A console application in which a real estate agent manages houses and apartments offered for rent or for purchase: adding properties, listing available or all properties, and marking a property as sold or rented.

## How to compile and run

Run all commands from the project root (the folder that contains `src`).

**macOS / Linux**
```
javac -d out $(find src -name "*.java")
java -cp out edu.uni.realestate.app.Main
java -cp out edu.uni.realestate.app.AgentDemo
```

**Windows (Command Prompt)**
```
dir /s /b src\*.java > sources.txt
javac -d out @sources.txt
java -cp out edu.uni.realestate.app.Main
java -cp out edu.uni.realestate.app.AgentDemo
```

`Main` runs the interactive menu; `AgentDemo` runs the automated tests without keyboard input.

## Project structure

```
src/edu/uni/realestate/
├── model/     Property hierarchy, RentalTerms, PurchaseTerms, enums, InputHelper
├── service/   Agent
└── app/       Main (menu) and AgentDemo (tests)
```

Dependencies point one way: `app` → `service` → `model`.

## Reflection Questions

### 1. The setters in this design are private. What would break, or which rule could be bypassed, if `setBedrooms` were public?

If `setBedrooms` were public, it would allow editing after creation and any class holding a reference to a property could change it, which would break the rule established in the assignment brief that editing a property is out of scope. In our design this rule is enforced through private setters: only the constructor calls them, so the data is set once and never altered again. The validation rule would survive, since `setBedrooms` would still reject negative values, but nothing would stop a valid edit. Our `Agent.getProperties()` returns `Collections.unmodifiableList(properties)`, but that only protects the list, not the objects inside it, so `Main` could call `agent.getProperties().get(0).setBedrooms(5)` and bypass `Agent` completely. For the same reason `Property` has `close()` but no public `setAvailable(boolean)`, which would let a sold or rented property be reactivated.

### 2. `HouseRental` and `ApartmentRental` both hold a `RentalTerms` object instead of inheriting rental fields. Why can Java not solve this with inheritance alone, and what would the code look like without composition?

Java only supports single inheritance: a class can extend only one class. As `HouseRental` already extends `House`, it can't also inherit rental fields from a `Rental` class. Without composition, we would have to copy the rental fields into every rental class: `monthlyRent`, `furnished` and `utilities`, with their getters, validating setters and display lines, into both `HouseRental` and `ApartmentRental`, and `price` and `annualTaxes` into both purchase classes, so any change to a rule would have to be made twice. On the other hand, with composition, `RentalTerms` and `PurchaseTerms` are written only once, and both rental classes use it: each concrete class holds a `private final` terms object created in its constructor with `terms = new RentalTerms(data)`. Its `display()` then calls `super.display()` for the property lines and `terms.display()` for the deal lines, so no code is duplicated.

### 3. `promptInit` is static, so `HouseRental.promptInit` hides `House.promptInit` rather than overriding it. Explain the difference and why an instance method could not be used here.

Overriding is decided by the real object at runtime, while hiding is decided by the class name you write. Overriding applies to instance methods and is decided at runtime by the object's actual class: when `Agent.displayProperties` calls `p.display()` on a `Property` reference, Java runs the `HouseRental` version if that is what the object really is. Hiding applies to static methods, which belong to the class rather than to an object, so the method is chosen at compile time from the class name written in the call. `HouseRental.promptInit` therefore does not replace `House.promptInit`: both exist side by side, `HouseRental.promptInit` calls `House.promptInit(in)` explicitly to reuse it, and adding `@Override` to `promptInit` would be a compile error. An instance method could not be used because `promptInit` runs before the object exists: its job is to collect the map the constructor needs, and an instance method would need an object that cannot be created without that map. This is also why our `Agent` registry can store `HouseRental::promptInit` and call it without any property object.

### 4. Passing a `Map<String, String>` to constructors is flexible but not type-safe. Give one error the compiler would catch with a normal constructor but cannot catch with the map.



### 5. The model classes read from a `Scanner`. If the agent later moves to a GUI or web page, which classes must change? Propose where the prompting code should live instead.



## AgentDemo Output

```
=== 1. Creating one property of each concrete class ===
Properties added: 4, available: 4

PROPERTY #1 [AVAILABLE]
  Square feet: 1400
  Bedrooms:    3
  Bathrooms:   2
  Stories:     2
  Garage:      ATTACHED
  Fenced yard: yes
  Monthly rent: 1850.00 EUR
  Furnished:    no
  Utilities:    about 120 EUR/month

PROPERTY #2 [AVAILABLE]
  Square feet: 2100
  Bedrooms:    4
  Bathrooms:   3
  Stories:     2
  Garage:      DETACHED
  Fenced yard: no
  Price:        450000.00 EUR
  Annual taxes: 3200.00 EUR

PROPERTY #3 [AVAILABLE]
  Square feet: 650
  Bedrooms:    0
  Bathrooms:   1
  Balcony:     yes
  Laundry:     COIN
  Monthly rent: 950.00 EUR
  Furnished:    yes
  Utilities:    included

PROPERTY #4 [AVAILABLE]
  Square feet: 900
  Bedrooms:    2
  Bathrooms:   1
  Balcony:     no
  Laundry:     ENSUITE
  Price:        280000.00 EUR
  Annual taxes: 0.00 EUR


=== 2. Invalid values are rejected ===
Square feet = 0: rejected -> Square feet must be at least 1
Bedrooms = -1: rejected -> Bedrooms cannot be negative
Bathrooms = 0: rejected -> Bathrooms must be at least 1
Stories = 0: rejected -> Stories must be at least 1
Garage = "carport": rejected -> No enum constant edu.uni.realestate.model.GarageType.CARPORT
Laundry = "shared": rejected -> No enum constant edu.uni.realestate.model.LaundryType.SHARED
Monthly rent = 0: rejected -> Monthly rent must be greater than 0
Utilities = blank: rejected -> Utilities description is required
Price = -5000: rejected -> Price must be greater than 0
Annual taxes = -1: rejected -> Annual taxes cannot be negative
Properties in the agent's list: 4 (no rejected property was added)

=== 3. Closing a property twice is rejected ===
closeDeal(1), first time:  true
closeDeal(1), second time: false
closeDeal(99), unknown id: false
Direct close() again: rejected -> Property #1 is already RENTED
closeDeal(4): true

=== 4. Closed properties disappear from the available list ===
Available: 2 of 4

-- Available properties --
PROPERTY #2 [AVAILABLE]
  Square feet: 2100
  Bedrooms:    4
  Bathrooms:   3
  Stories:     2
  Garage:      DETACHED
  Fenced yard: no
  Price:        450000.00 EUR
  Annual taxes: 3200.00 EUR

PROPERTY #3 [AVAILABLE]
  Square feet: 650
  Bedrooms:    0
  Bathrooms:   1
  Balcony:     yes
  Laundry:     COIN
  Monthly rent: 950.00 EUR
  Furnished:    yes
  Utilities:    included

-- All properties --
PROPERTY #1 [RENTED]
  Square feet: 1400
  Bedrooms:    3
  Bathrooms:   2
  Stories:     2
  Garage:      ATTACHED
  Fenced yard: yes
  Monthly rent: 1850.00 EUR
  Furnished:    no
  Utilities:    about 120 EUR/month

PROPERTY #2 [AVAILABLE]
  Square feet: 2100
  Bedrooms:    4
  Bathrooms:   3
  Stories:     2
  Garage:      DETACHED
  Fenced yard: no
  Price:        450000.00 EUR
  Annual taxes: 3200.00 EUR

PROPERTY #3 [AVAILABLE]
  Square feet: 650
  Bedrooms:    0
  Bathrooms:   1
  Balcony:     yes
  Laundry:     COIN
  Monthly rent: 950.00 EUR
  Furnished:    yes
  Utilities:    included

PROPERTY #4 [SOLD]
  Square feet: 900
  Bedrooms:    2
  Bathrooms:   1
  Balcony:     no
  Laundry:     ENSUITE
  Price:        280000.00 EUR
  Annual taxes: 0.00 EUR


=== 5. The agent's list cannot be modified from outside ===
getProperties().clear(): rejected (unmodifiable list)
```