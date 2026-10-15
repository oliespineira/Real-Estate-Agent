Computer Programming 2-Real Estate Assignment by Pairs (Olivia Espiñeira and Lara Iglesias)



*/ REFLECTION QUESTIONS
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

5. The model classes read from a Scanner. If the agent later moves to a GUI or web page, which
   classes must change? Propose where the prompting code should live instead.