
GitHub Markup
=============

[ ![Download](https://api.bintray.com/packages/eltonkola/maven/prefdatalibrary/images/download.svg) ](https://bintray.com/eltonkola/maven/prefdatalibrary/_latestVersion)

This is a simple project, that helps you save/load a set of simple objects from SharedPreferences.

This is not a replace to sqlite, or other storage options, but a simple utility you can use on your projects if you have a limited set of data

Also you have two simple, demo projects, one is a full demo, and the second only shows how to use the library with gradle and jcenter


How to use:
=============


1. Your apps must extendPrefDataApp, we need this to get the context at any time

 YourApp extends PrefDataApp{

 }

     <application
         android:name=".DemoApp"
         ..

2. create your pojo, this will be saved on shared preferences (serialized as json and deserialized)

public class JokeElement extends BaseType{

    private String title;
    private String description;
    private Date creation_date;

    ....

3. Now from PrefData, you can create/update/delete and list your objects, by using:

PrefData<JokeElement> dataPref = new PrefData<JokeElement>(DemoApp.getContext(), JokeElement.class);


    Create:

        JokeElement joke = new Joke();
        ....
        dataPref.create(joke)

    Delete:

        dataPref.delete(joke);

    List all:

        List<JokeElement> jokes = dataPref.getAll();

    Get by id:

        dataPref.getById(id)

    Update:

        dataPref.update(joke);

You can use as many pojos as you want
You don't have to extend our application as long as you provide a valid context to PrefData


Dependecies:
=============

- gson

Remember (for me)
=============

to publish we need in local.properties

sdk.dir=/home/elton/Android/Sdk
bintray.user=
bintray.apikey=
bintray.gpg.password=
bintray.oss.user=
bintray.oss.password=