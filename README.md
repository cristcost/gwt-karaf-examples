gwt-karaf-examples
==================

Selection of Sample projects of GWT adapted to run on Apache Karaf.

*Work in progress!*


Installation instructions (DRAFT):
```
git clone gwt 
git patch with https://gwt-review.googlesource.com/#/c/5351/
export GWT_VERSION=2.6.0-rc1 in order to be used as osgi export version
ant dist
cd maven
./push-gwt.sh

git clone this repo
mvn clean install
download servicemix http://www.apache.org/dyn/closer.cgi?path=servicemix/servicemix-4/4.5.3/apache-servicemix-4.5.3.tar.gz
tar xzf apache-servicemix-4.5.3.tar.gz
cd servicemix
bin/servicemix
features:addurl mvn:com.google.gwt.samples.karaf/features/1.0.0-SNAPSHOT/xml/features
features:install gwt-samples-<tab> to show availabel feature

open from your browser one of the examples:
http://localhost:8181/gwt-karaf-starter/
http://localhost:8181/gwt-karaf-validation/
http://localhost:8181/gwt-karaf-dynatable/
http://localhost:8181/gwt-karaf-dynatablerf/
http://localhost:8181/gwt-karaf-mobilewebapp/
```

Limitations
-----------
Actually, gwt-karaf-dynatablerf and gwt-karaf-mobilewebapp don't work if started 
together due to a problem with requestfactory class loading: as the first of the 
two application receive a requestfactory request, the other application is unable 
to work properly and a full restart of the osgi environment is required.

I'll explore this issue during the development of https://github.com/cristcost/sensormix






