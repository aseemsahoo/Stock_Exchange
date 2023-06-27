<a name="readme-top"></a>
# Stock_Exchange
This project simulates the real-time stock market and allows for paper trading. It provides the users a platform for accessing the price history of various stocks and the provision to buy/sell stocks as well.

APIs are used to pull real-time data from the server and, if users buy/sell any stocks, the API makes sure the CRUD operation is invoked accordingly. For more information about APIs, please scroll down to the API section

# Table of contents
<!--ts-->
   * [Implementaion](#implementaion)
      * [Login Page](#login-page)
      * [User Dashboard Page](#user-dashboard-page)
      * [Stocks display Page](#stocks-display-page)
      * [Portfolio Page](#portfolio-page)
   * [Application programming interface (API)](#application-programming-interface-api)
      * [Registration API](#registration-api)
      * [User Dashboard API](#user-dashboard-api)
      * [Stocks Display API](#stocks-display-api)
      * [Accounts API](#accounts-api)
   * [Technologies & Frameworks](#technologies--frameworks)
   * [Prerequisites](#prerequisites)
   * [Installation](#installation)
   * [Acknowledgments](#acknowledgments)
<!--te-->

---

# Implementaion
### Login Page

Currently it does not support 'Reset/Forgot Password'. It will be added soon. 




### User Dashboard Page
Upon successful login, the user will be shown the list of all stocks. It also contains a search tab where you can search the stocks by any keyword.




### Stocks display Page
If the user wants to view the charts of any stock, then they will be redirected to this page.


### Portfolio Page
The user can view their portfolio where they can assess all their invested shares.


<p align="right">(<a href="#readme-top">back to top</a>)</p>

---

# Application programming interface (API)
### Login API
- Provide more fronts to let users login/register for this app

| Method   | URL                                      | Description                              |
| -------- | ---------------------------------------- | ---------------------------------------- |
| `POST`   | `/main/signupSubmit`                          | Register a new user                        |

### User Dashboard API
- Display all the stocks list and search as well.

| Method   | URL                                      | Description                              |
| -------- | ---------------------------------------- | ---------------------------------------- |
| `GET`    | `/main/dashboard/list?keyword={}`         | Search among the list of all stocks          |

### Stocks Display API
- See the price trend of the selected stock and buy/sell shares.

| Method   | URL                                      | Description                              |
| -------- | ---------------------------------------- | ---------------------------------------- |
| `GET`    | `/main/stocks/showCharts?stockId=1`           | Price trend of the selected company        |
| `POST`    | `/main/stocks/buy`                   | Buy some shares of the selected stock                       |
| `POST`   | `/main/stocks/sell`| Sell some/all shares of the selected stock|

### Accounts API
- Display the user profile

| Method   | URL                                      | Description                              |
| -------- | ---------------------------------------- | ---------------------------------------- |
| `GET`    | `/main/accounts/list?userId={}`           | Load the user portfolio        |

<p align="right">(<a href="#readme-top">back to top</a>)</p>

---

# Technologies & Frameworks
This project is created with:
* SpringBoot 3.0+
* Java JDK 9
* MySQL

Frameworks used are:
- MySQL connector
- Thymeleaf

<p align="right">(<a href="#readme-top">back to top</a>)</p>

---

# Prerequisites
You need the following installed on your desktop:
- [Java JDK 11 and above](https://www.oracle.com/in/java/technologies/javase/jdk11-archive-downloads.html)
- [IntelliJ Community](https://www.jetbrains.com/idea/download/) (for development purposes only)

# Installation

Username: aseem
Password: sahoo

**I am a tester:** 
- Download the latest build from the [releases](https://github.com/aseemsahoo/Stock_Exchange/releases) section. Run the .jar application.

**I am a developer:** 
- Clone this repository
- Open this project in Intellij or Eclipse.
- Use the endpoints (mentioned above with credentials) for Postman testing

<p align="right">(<a href="#readme-top">back to top</a>)</p>

---
