ORM
====================
Android ORM solution
--------------------

Since working with the Android SQLite cursor is a tedious task with a lot of boilerplate code and
footwork, a need for the automation of the process arose. There are several complete or nearly
complete solutions readily available to the general public.

We have tried some of these solutions such as Sugar ORM(v1.3.1), ActiveAndroid(v3.1.0-SNAPSHOT), 
Realm (v0.80.1) and GreenDao (v1.3). Only Sugar ORM satisfied all of our requirements. ActiveAndroid 
would not allow the setting of a custom object id which resulted in an impossibility of implementing 
parcelable interface. GreenDao is a code generator, while Realm was too limited.

The goal is to create an example android application that would show the benefits and the usage
of Sugar ORM for creating a SQLite database along with CRUD operations. The example would be
demonstrated on a simple model covering all the possible relationships of a relational database.

This toolbox is split into three parts:
1. The model
2. Service classes
3. UI code for demonstration purposes.

The model contains the definition of all the database tables. The important part to note is that the
model package is marked in the android manifest under DOMAIN_PACKAGE_NAME meta tag. Sugar ORM is
using that tag to detect all the models, and therefore all the models need to be located in the same
package. Sugar ORM is using the reflection to populate the database. Another important thing to note
is that table columns will named using the SQL nomenclature.
(Ex. dateTime field becomes date_time column)
Sugar ORM supports only one-to-many relationships which means that many-to-many relationships are
decomposed to two one-to-many relationships as demonstrated by the "Connection" model.

Service classes contain all the queries used throughout the application. Two types of the queries are
demonstrated:
 - A simple query (find method) that takes a three parameters: Return type, whereClause
   and query parameters.
 - A query using a predefined query statement (findWithQuery method) that takes the return type and
   a full query statement as a string.

Sugar ORM also supports database migration which was not included in this toolbox. To migrate the
database to another version, simply increment the meta-tag VERSION by 1 and include the properly
named SQL file with desired scrips (Ex. Migrating to version 3 would mean that the SQL file needs
to be named "3.sql"). Sugar will automatically create tables for new entities, so the migration
script only needs to cater for alterations to existing tables. An important thing to note is that
the version meta-tag has to have a minimum version of 2 or the database wont be created.

Instrumentation tests with Jenkins CI
======================================

A machine running CPU with Nehalem architecture or better (Any i3, i5, i7 will suffice) is
required in order to allow virtualization of the android emulator. Once the machine is set as a
Jenkins slave, it will also require Java, Git, Android SDK (installed by Android plugin for jenkins
automatically).

Gradle tasks that need to be run:
:app:assembleDebug :app:assembleDebugAndroidTest

Important: Once the building is complete, BOTH app-debug.apk AND app-debug-androidTest-unaligned.apk
----------------------------------------------------------------------------------------------------
need to be installed.
---------------------

Once both APKs have been installed, running test is simple:
adb shell am instrument -w eu.execom.toolbox1sugarorm.test/android.test.InstrumentationTestRunner