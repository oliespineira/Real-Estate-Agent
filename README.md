Computer Programming 2-Real Estate Assignment by Pairs (Olivia Espiñeira and Lara Iglesias)



##REFLECTION QUESTIONS
1. The setters in this design are private. What would break, or which rule could be bypassed, if
setBedrooms were public?

If setBedrooms were public, it would allow editing after creation. This breaks the rule established in assignment brief that editing a property is out of scope, once a property is created it can't be changed. 
This condition is enforced through private setters, only the constructor can call them, so the data is set once and not altered again. 
Nonetheless, the validation rule would still survive as the setter still rejects negative values.  

2. HouseRental and ApartmentRental both hold a RentalTerms object instead of inheriting
   rental fields. Why can Java not solve this with inheritance alone, and what would the code look
   like without composition?

Java only supports single inheritance, a class can extend only one class. 
As 'HouseRental' already extends 'House', it can't inherit rental fields from somewhere else. 
And without composition, you'd be copying the rental fields into every rental class. 
However, with composition 'RentalTerms' would be written only once and both rental classes use it. 

3. promptInit is static, so HouseRental.promptInit hides House.promptInit rather than
   overriding it. Explain the difference and why an instance method could not be used here.

The difference between hiding and overriding is that
And we couldn't use an instance method here because 

4. Passing a Map<String, String> to constructors is flexible but not type-safe. Give one error the
   compiler would catch with a normal constructor but cannot catch with the map.

5. The model classes read from a Scanner. If the agent later moves to a GUI or web page, which
   classes must change? Propose where the prompting code should live instead.



##AGENT DEMO OUTPUT
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

Process finished with exit code 0