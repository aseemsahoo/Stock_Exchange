<a name="readme-top"></a>
# Stock_Exchange
This project simulates the real-time stock market and allows for paper trading. It provides the users a platform for accessing the price history of various stocks and the provision to buy/sell stocks as well.

APIs are used to pull real-time data from the server and, if users buy/sell any stocks, the API makes sure the CRUD operation is invoked accordingly. For more information about APIs, please scroll down to the API section

# Table of contents
<!--ts-->
   * [Implementaion](#implementaion)
      * [User Dashboard Page](#user-dashboard-page)
      * [Stocks display Page](#stocks-display-page)
      * [Portfolio Page](#portfolio-page)
   * [Application programming interface (API)](#application-programming-interface-api)
      * [Login API](#login-api)
      * [User Dashboard API](#user-dashboard-api)
      * [Stocks Display API](#stocks-display-api)
      * [Accounts API](#accounts-api)
   * [Technologies & Frameworks](#technologies--frameworks)
   * [Prerequisites](#prerequisites)
   * [Installation](#installation)
<!--te-->

---

# Implementaion
### User Dashboard Page
Upon successful login, the user will be shown the list of all stocks. It also contains a search tab where you can search the stocks by any keyword.

![](https://imgur.com/sWEJAD3.jpg)

### Stocks display Page
If the user wants to view the charts of any stock, then they will be redirected to this page.

![](https://imgur.com/EiU4Zir.jpg)

### Portfolio Page
The user can view their portfolio where they can assess all their invested shares.

![](https://imgur.com/KFrGbtF.jpg)

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
| `GET`    | `/main/stocks/showCharts?stockId={}`           | Price trend of the selected company        |
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
* Java JDK 11
* SpringBoot 3.0
* MySQL DB

Frameworks used are:
- MySQL connector
- Thymeleaf
- Hibernate
- Spring Data JPA
- Thymeleaf

---

# Prerequisites
You need the following installed on your desktop:
- [Java JDK 11 and above](https://www.oracle.com/in/java/technologies/javase/jdk11-archive-downloads.html)
- [IntelliJ Community](https://www.jetbrains.com/idea/download/) (for development purposes only)

# Installation

Credentials to bypass spring security:

Username: aseem

Password: sahoo

**I am a tester:** 
- Download the latest build from the [releases](https://github.com/aseemsahoo/Stock_Exchange/releases) section. Run the .jar application.
- Use the above credentials to login for the application to start.

**I am a developer:** 
- Clone this repository
- Open this project in Intellij or Eclipse.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

---
