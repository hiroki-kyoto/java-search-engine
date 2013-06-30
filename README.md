java-search-engine
==================

this is a search engine using java to build a personalized tool.

the FlyUp project is build for the test of the java search based on lucene core,
and the most important part of it, is a example of collecting the entries from the 
database. this is called ColdStart part of the project. But I think this is purhap
s won't work fine because the indexing time is too long and the entries are too much
to fully obtained. While how to deal with the time delay of the search engine?

I have got an idea, that is just divide the entries into different fields and each only
store some of the total, then with the concurrent method of querying process, we could
manage to build a fast-response server of searching.

For the PurpleMan project, the core part is a server that simplely answer the request
of key-words-queries. Because the remote server's useable ports are already taken and 
the others are blocked, so I build the mid-man to transfer the query key-words and the 
response xml.

While the last part is a listener that wwait for the web crawler to draw entries from
posting web. Thus once the items are going to be updated, my crawler will have a backup
to store in the remote server and the volume is much more smller due to my later process
of the data.

Fro more of my projects' infomration, just mail me at supershameman@gmail.com or at 
1981865127@qq.com, it's easy to contact me if you want. Also you can leave me a message
here. I will find time to review it!

Thanks for your time!
