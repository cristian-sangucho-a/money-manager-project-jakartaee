# Money Manager Project - Java EE

## 📖 Description
The **Money Manager** project is a web application developed in **Java EE** that allows users to manage their personal finances. Users can:
- Register income, expenses, and account transfers.
- View a dashboard summarizing their financial movements.

---

## 🛠️ Technology
This project uses the following technologies:

- **Java EE**: Business logic and data persistence development.
- **Jakarta Persistence API (JPA)**: Data persistence management.
- **Jakarta Servlet**: HTTP request management.
- **JSP (JavaServer Pages)**: Dynamic content generation.
- **CSS**: User interface design and styling.
- **Maven**: Dependency management and project building.
- **MySQL**: Storage of financial information.

---

## 📦 Dependencies
Main dependencies used in the project:

- `jakarta.servlet.jsp.jstl-api`: Version **3.0.0**
- `jakarta.el-api`: Version **6.0.0**
- `jakarta.persistence-api`: Version **3.1.0**
- `mysql-connector-j`: Version **8.3.0**
- `eclipselink`: Version **4.0.2**
- `jersey-container-servlet`: Version **3.1.2**

---

## 📂 Project Structure
The main project structure is as follows:

```
src/main/java/com/miapp/controller      # Controllers
src/main/java/com/miapp/model           # Models
src/main/webapp/jsp                     # Views (JSP)
src/main/webapp/styles                  # Styles (CSS)
src/main/resources/META-INF             # Configuration (persistence.xml)
src/main/sql                            # SQL Scripts
```

---

## 🗄️ Database Configuration
The project uses **MySQL** as the database. The configuration is located in the `persistence.xml` file, specifying:

- **Connection URL**.
- **User** and **password**.
- **JDBC Driver**.

---

## 📜 SQL Scripts
The project includes an SQL script to load initial data. This file, named `load-data.sql`, contains:

- Initial categories for income, expenses, and transfers.
- Default accounts.

---

## 🚀 Controllers
The main controller is `ContabilidadController.java`, responsible for:
- Handling HTTP requests.
- Coordinating the interaction between the view and the model.

---

## 🌟 Views
The views of the project are implemented with **JSP** and located in the `jsp` directory. Key views include:

- `actualizarmovimiento.jsp`
- `registrartransferencia.jsp`
- `registraregreso.jsp`
- `registraringreso.jsp`
- `verdashboard.jsp`
- `vercategoria.jsp`
- `vercuenta.jsp`

---

## 🎨 Styles
The CSS styles are located in the `src/main/webapp/styles` directory. Main files include:

- `stylesactualizarmovimiento.css`
- `stylesregistrarmovimiento.css`
- `stylesverdashboard.css`
- `stylesvercuenta1.css`

---

## 📝 How to Run the Project
1. Clone the repository to your local machine.
  ```
  git clone https://github.com/cristian-sangucho-a/money-manager-project-jakartaee.git
  ```
3. Configure the **MySQL** database and update credentials in `persistence.xml`.
4. Import the project into your IDE (e.g., Eclipse).
5. Run the project on a **Java EE**-compatible server (e.g., Apache Tomcat).
6. Access the application in your web browser.

---

## 📄 License
This project is licensed under the **GPL-3.0 license**. See the `LICENSE` file for more details.
