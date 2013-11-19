gwt-karaf-examples
==================

Selection of Sample projects of GWT adapted to run on Apache Karaf


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
