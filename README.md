# Course Registration System

## Overview
This is a group project of 7 people. A course registration system for the Computer Engineering department in Marmara University is thoroughly designed and implemented by following iterative development. The project is implemented in three iterations and each iteration takes three weeks. In the first iteration, the project is implemented in java. Later, more requirements are requested for the course registration system and new features are added in the second iteration. Lastly, in the third iteration, we are requested to write the whole project in python which requires an extensive conversion from java to python with also a few new features. This system is currently using command line interface for the UI.

Each iteration is followed by requirement analysis, design, implementation and testing. For the first two part, we extensively used Miro to work together while drawing sequence diagrams and UML class diagram. For simplicity, no real database is used in this project. Instead, json files are used to hold the data. A class called DatabaseManager that we created acts like a database engine by reading and modifying json files. Object oriented programming is highly achieved in this project with many classes.

## About the Project
There are three roles in the system: student, advisor and admin. Each have their own responsibilities and own command line interface. System starts with a login page, all users must login to the system by username and password.

### Student
Students basically can add and delete courses for registration, they can see their timetable and send selected courses to her/his advisor for approval.

Selected courses

![my courses](https://github.com/ItsYusufDemir/CSE3063F23P1_GRP2/assets/104091838/dcd86f1b-0569-4f7e-8754-b51dfd3467fa)

<br />


Adding new course

https://github.com/ItsYusufDemir/CSE3063F23P1_GRP2/assets/104091838/3af1d7e9-b343-44ac-9ef7-112ee44492ad

<br />
  
Courses are sent for approval

![Sended](https://github.com/ItsYusufDemir/CSE3063F23P1_GRP2/assets/104091838/20362a40-43e5-4ef2-84ab-2a45b2c98ccf)

### Advisor
Advisors can see the selected courses of all of her/his students. They can see the timetable and possible conflicts of courses. They are able to approve or reject courses of students. After the approval/rejection, they can finalize the registration of students.

After the login process, advisor sees their notifications. When a approval request comes from a student, a notification is sent to her/his advisor.

![notification](https://github.com/ItsYusufDemir/CSE3063F23P1_GRP2/assets/104091838/309581c7-34e5-4c10-8269-548c36256619)

Then advisor selects a student and see her/his courses. Advisor approves/rejects courses.

![advisor panel](https://github.com/ItsYusufDemir/CSE3063F23P1_GRP2/assets/104091838/bf989885-b17c-4548-bbcb-1cc6cee7c55f)

When the approval is done, advisor finalize the registration. student recieves a notification about it.

### Admin

Admin is responsible for changing constraints of the system: registration week enabled, number of courses student select per term, minimum required ECTS credit to select graduate project course and add-drop week enabled. They can also add or remove course to the system.

![consraints](https://github.com/ItsYusufDemir/CSE3063F23P1_GRP2/assets/104091838/310d2709-74b7-49ce-9bff-4034f2af36f3)






