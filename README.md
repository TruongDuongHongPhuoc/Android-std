<h1> ANDROID APPLICATION </h1>


<h1> INTRODUCTION</h1>
I have developed an application using Android Studio and MAUI.NET, integrated with SQLite and Firebase to allow seamless synchronization between two apps. The system manages courses and classes, where each course can have multiple classes. Key features include CRUD operations, search, add to cart, and backlog functionality for users. Notably, the admin app, built with Android Studio, ensures offline changes are synchronized with Firebase once the admin device reconnects to the internet.
<h2>Use Case</h2>

![image](https://github.com/user-attachments/assets/6c8cd74f-cb87-4bd2-9adc-93f6f1744608)
<h2>Entity relationship điagram</h2>

![image](https://github.com/user-attachments/assets/9a5a79a0-758f-4433-afb8-7b6bb978680c)

<h2>TEST CASES</h2>

<table border="1">
  <thead>
    <tr>
      <th>Test ID</th>
      <th>Test Case</th>
      <th>Test Input</th>
      <th>Expected Result</th>
      <th>Actual Result</th>
      <th>Status</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>1</td>
      <td>Verify that user able to Create Course in Admin app with valid information.</td>
      <td>Course Name: “Morning Yoga”<br>Start time: “6h20”<br>Duration: “30 minutes”<br>Capability: “15”<br>Price a class: “200$”<br>Type: “Family yoga”<br>Description: “you should bring your bottle of water”<br>Days of week: “Monday”</td>
      <td>Course is created successfully and a confirmation message is displayed: "Course created successfully."</td>
      <td>Course is created successfully and a confirmation message is displayed: "Course created successfully."</td>
      <td>Pass</td>
    </tr>
    <tr>
      <td>2</td>
      <td>Verify that user aren’t able to create/update course in admin app with invalid information</td>
      <td>Course Name: “Morning Yoga”<br>Start time: “”<br>Duration: “30 minutes”<br>Capability: “15”<br>Price a class: “200$”<br>Type: “Family yoga”<br>Description: “you should bring your bottle of water”<br>Days of week: “Monday”</td>
      <td>Error message display: “Start time cannot be null”</td>
      <td>Error message display: “Start time cannot be null”</td>
      <td>Pass</td>
    </tr>
    <tr>
      <td>3</td>
      <td>Verify that users aren’t able to create/update course if the price smaller or equal to zero</td>
      <td>Course Name: “Morning Yoga”<br>Start time: “”<br>Duration: “30 minutes”<br>Capability: “15”<br>Price a class: “0$”<br>Type: “Family yoga”<br>Description: “you should bring your bottle of water”<br>Days of week: “Monday”</td>
      <td>Error message display: “Price cannot be lower than zero”</td>
      <td>Error message display: “Price cannot be lower than zero”</td>
      <td>Pass</td>
    </tr>
    <tr>
      <td>4</td>
      <td>Verify that user are able to update course with valid information</td>
      <td>Course Name: “Morning traditional Yoga”<br>Start time: “6h30”<br>Duration: “45 minutes”<br>Capability: “10”<br>Price a class: “250$”<br>Type: “Family yoga”<br>Description: “you should bring your bottle of water.”<br>Days of week: “Monday, Tuesday”</td>
      <td>Course details are updated successfully</td>
      <td>Course details are updated successfully</td>
      <td>Pass</td>
    </tr>
    <tr>
      <td>5</td>
      <td>Verify that user is able to view course detail before create</td>
      <td>Course Name: “Morning Yoga”<br>Start time: “6h20”<br>Duration: “30 minutes”<br>Capability: “15”<br>Price a class: “200$”<br>Type: “Family yoga”<br>Description: “you should bring your bottle of water”<br>Days of week: “Monday”</td>
      <td>All entered course details are displayed correctly for review before creation.</td>
      <td>All entered course details are displayed correctly for review before creation.</td>
      <td>Pass</td>
    </tr>
    <tr>
      <td>6</td>
      <td>Verify that user is able to use navigation to change view page</td>
      <td>Open navigation select yoga class manage</td>
      <td>User is navigated to the yoga class management page.</td>
      <td>User is navigated to the yoga class management page.</td>
      <td>Pass</td>
    </tr>
    <tr>
      <td>7</td>
      <td>Verify that user is able to create yoga class with valid information</td>
      <td>Name: “Morning yoga 1”<br>Teacher: “Tony”<br>Date: “12/11/2024”<br>Course: “Morning yoga”<br>Description: “No additional comment”</td>
      <td>Yoga class is created successfully and a confirmation message is displayed: "Yoga class created successfully."</td>
      <td>Yoga class is created successfully and a confirmation message is displayed: "Yoga class created successfully."</td>
      <td>Pass</td>
    </tr>
    <tr>
      <td>8</td>
      <td>Verify that user able to Update yoga class with valid information</td>
      <td>Name: “Morning yoga 1”<br>Teacher: “Tony”<br>Date: “12/11/2024”<br>Course: “Morning yoga”<br>Description: “No additional comment”</td>
      <td>Yoga class details are updated successfully and a confirmation message is displayed: "Yoga class updated successfully."</td>
      <td>Yoga class details are updated successfully and a confirmation message is displayed: "Yoga class updated successfully."</td>
      <td>Pass</td>
    </tr>
    <tr>
      <td>9</td>
      <td>Verify that user aren’t able to create/ update yoga class with invalid information</td>
      <td>Name: “Morning yoga 1”<br>Teacher: “”<br>Date: “12/11/2024”<br>Course: “Morning yoga”<br>Description: “No additional comment”</td>
      <td>Error message is displayed: "Teacher cannot be null." Yoga class is not created or updated.</td>
      <td>Error message is displayed: "Teacher cannot be null." Yoga class is not created or updated.</td>
      <td>Pass</td>
    </tr>
    <tr>
      <td>10</td>
      <td>Verify that user are able to search specific class by teacher name</td>
      <td>Search “Tony”</td>
      <td>Class Taught by Tony displayed</td>
      <td>Class Taught by Tony displayed</td>
      <td>Pass</td>
    </tr>
    <tr>
      <td>11</td>
      <td>Verify that user able to delete yoga class</td>
      <td>Select a yoga class and delete</td>
      <td>Selected yoga class is deleted successfully</td>
      <td>Selected yoga class is deleted successfully</td>
      <td>Pass</td>
    </tr>
    <tr>
      <td>12</td>
      <td>Verify that user able to delete course</td>
      <td>Select a Course and delete</td>
      <td>Selected Course deleted</td>
      <td>Selected course deleted successfully</td>
      <td>Pass</td>
    </tr>
    <tr>
      <td>13</td>
      <td>Verify that when course deleted all relative class will be deleted</td>
      <td>Selected course and Delete</td>
      <td>Course selected and yoga class relative deleted</td>
      <td>Course selected and yoga class relative deleted</td>
      <td>Pass</td>
    </tr>
    <tr>
      <td>14</td>
      <td>Verify that when user device connects to internet database will be synchronized to Firebase</td>
      <td>The user device connect to internet</td>
      <td>Information from Sqlite synchronize to Firebase</td>
      <td>Information from Sqlite synchronize to Firebase</td>
      <td>Pass</td>
    </tr>
    <tr>
      <td>15</td>
      <td>Verify that when the instance deleted while device offline when back to online the deleted instance deleted in firebase</td>
      <td>Select yoga class and delete while device is offline and back to online later</td>
      <td>Firebase delete yoga class which was deleted</td>
      <td>Firebase delete yoga class which was deleted</td>
      <td>Pass</td>
    </tr>
    <tr>
      <td>16</td>
      <td>Verify that user able to registration in Client app</td>
      <td>In Client app Sign up a new account</td>
      <td>Notify user success create new account</td>
      <td>Notify user success create new account</td>
      <td>Pass</td>
    </tr>
    <tr>
      <td>17</td>
      <td>Verify that the user able to log in with valid information in the client app</td>
      <td>In Client app Log in with created account.</td>
      <td>Navigate user to Homepage</td>
      <td>Navigate user to Homepage</td>
      <td>Pass</td>
    </tr>
    <tr>
      <td>18</td>
      <td>Verify that user able to view all yoga class available in client app</td>
      <td>In client app login in with created account</td>
      <td>Display all possible yoga classes</td>
      <td>Display all possible yoga classes</td>
      <td>Pass</td>
    </tr>
    <tr>
      <td>19</td>
      <td>Verify that user able to add item to cart and purchase classes in client app</td>
      <td>Select add to cart button on a yoga class</td>
      <td>A alert notify success add to cart</td>
      <td>A alert notify success add to cart</td>
      <td>Pass</td>
    </tr>
    <tr>
      <td>20</td>
      <td>Verify that user able to search class instance by date</td>
      <td>Input search bar: “9/” and hit button search</td>
      <td>Display yoga classes which have date of 9/</td>
      <td>Display yoga classes which have date of 9/</td>
      <td>Pass</td>
    </tr>
    <tr>
      <td>21</td>
      <td>Verify that user able to view class they have booked</td>
      <td>In cart view hit button Purchase log</td>
      <td>Navigate to purchase log view and view all class which booked</td>
      <td>Navigate to purchase log view and view all class which booked</td>
      <td>Pass</td>
    </tr>
    <tr>
      <td>22</td>
      <td>Verify that user able to view class they have purchased</td>
      <td>In cart view purchase log</td>
      <td>Navigate to purchase log view and view all class purchased</td>
      <td>Navigate to purchase log view and view all class purchased</td>
      <td>Pass</td>
    </tr>
  </tbody>
</table>


<h2> result</h2>
<h3>ANDROID STD</h3>
![image](https://github.com/user-attachments/assets/dbc16cc4-7e72-45b1-8b13-492788471d0e)
![image](https://github.com/user-attachments/assets/c11bb6bd-5a5b-42b3-81e6-6432e0d29dc5)
![image](https://github.com/user-attachments/assets/d1a06656-81a3-4fc7-92fd-125d3639dc3b)
![image](https://github.com/user-attachments/assets/28d7d9d4-3b89-4a53-bd21-7d1ab523a65f)
![image](https://github.com/user-attachments/assets/adec0be0-7c25-490c-ad20-4d1d6cb456f6)
![image](https://github.com/user-attachments/assets/03ec3713-020d-4b4a-a28a-6dba54237fe1)
![image](https://github.com/user-attachments/assets/a8b848fa-7287-42be-9b45-140ae189d67a)
![image](https://github.com/user-attachments/assets/cdc38ba7-70d6-4951-8adf-f642e352d5f6)
![image](https://github.com/user-attachments/assets/b5146941-c9e7-4b46-a160-132f17cfb33d)
![image](https://github.com/user-attachments/assets/a38f46d9-01d5-426b-8d44-8b9986303c44)
![image](https://github.com/user-attachments/assets/150e1629-e991-41ba-adeb-14f448040e96)
![image](https://github.com/user-attachments/assets/bb3a415c-e315-4411-bcab-c6ba706591dd)

<h3>MAUI APP</h3>

![image](https://github.com/user-attachments/assets/80348dc0-0cf8-4da3-b0aa-5140328d75be)
![image](https://github.com/user-attachments/assets/5eb5cc4b-a1b7-4216-9817-86af72d8e2d5)
![image](https://github.com/user-attachments/assets/198cadde-bda8-4109-8de2-4da530b2d971)
![image](https://github.com/user-attachments/assets/2bc6561a-f54a-4307-b71b-d2a9da48584f)
![image](https://github.com/user-attachments/assets/3a27e097-f9e5-48cc-8940-5678770670e3)
