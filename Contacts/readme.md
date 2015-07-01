Toolbox #2: Contact management
===================================
CRUD operations over the android native contact database
---------------------------------------------------------

Social integrations are a common part of today’s android application and as such, it is sometimes
required to traverse across the user’s contact records so that they could be used in the application.

The objective is to show the basics of gaining access to the local contact database and doing CRUD
operations on both contacts and their contact information. The example has to cover at least a few
different types of contact information to establish a pattern of accessing and modifying desired
contact information entries.

The native android contact database is complex. The most important table is the "Data" table.
Some of the columns are:
* mimetype - contains the type of the contact information: email, display name, phone number, icon...
* contact_id - the ID of the contact to whom the information belongs.
* data 1 to data 15 - contains all the details about the information.

This toolbox demonstrates the reading, inserting and updating native contacts via the two main
classes: ContactService (For contacts themselves) and InfoService (For managing contact information
of the said contacts).