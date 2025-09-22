# Entity Relationship Diagram (ERD)
This document provides the ERD for the Farm Web Application along with an entity list, attributes, and relationships.

## Entity List
1. **User**
   - userId: Long
   - username: String
   - password: String
   - email: String
   - role: String (Customer/Admin)

2. **Product**
   - productId: Long
   - name: String
   - description: String
   - price: Decimal
   - availability: Boolean

3. **Order**
   - orderId: Long
   - userId: Long (FK)
   - productId: Long (FK)
   - status: String
   - totalAmount: Decimal
   - orderDate: Date

4. **Media**
   - mediaId: Long
   - url: String
   - type: String (Photo/Video)

5. **Reservation**
   - reservationId: Long
   - userId: Long (FK)
   - productId: Long (FK)
   - reservationDate: Date

## Relationships
- User can have many Orders.
- User can have many Reservations.
- Product can belong to many Orders and Reservations.
- Media is associated with Products as a gallery.