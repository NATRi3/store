## [Watch store](https://watchstoreepam.herokuapp.com)

## How it's works:
Guest can find what he wants to buy. Then register and receive activation link on email. Then guest singing in and their status changes on user.User adding products to cart, manage their account and leave feedbacks on products.Admin can create new products, news, collections, admins. Admin can veiw orders and manage their.
## Project features:
* User friendly URL
* Localization: en_EN, ru_BY
* 3 roles
* custom tags
* Custom connection pool and proxy connections
* Transactions
* XSS and Double submit peventing filters
* Timer task for recover connections

function | ADMIN’S SCOPE | CLIENT’S SCOPE | GUEST’S SCOPE
---------| --------------|----------------|---------------
change language| * | * | * |
creating new admin | * |   |  
viewing a user list and their information | * |   |   
blocking,inblokung users | * |
change infomation and image news | * |
change infomation and image products | * |
logout | * | * |
change account image and password | * | * |
delete and create news | * |
create products | * |
veiwing account information | * | * |
change account image and password | * | * |
add and remove products to cart |   | * |
create order by cart |   | * |
create feedback on product |   | * |
singing in |   |   | *
register user |   |   | *
ativate account by activation link |   |   | *
change password by email |   |   | *
	    

