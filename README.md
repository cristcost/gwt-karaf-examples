gwt-karaf-examples
==================

Selection of Sample projects of GWT adapted to run on Apache Karaf.

*Work in progress!*


Installation instructions (DRAFT):
```
svn co http://google-web-toolkit.googlecode.com/svn/tools/ tools
mkdir -p tools/bnd/lib
wget -O tools/lib/bnd/bnd-1.50.0.jar http://search.maven.org/remotecontent?filepath=biz/aQute/bnd/1.50.0/bnd-1.50.0.jar

git clone https://gwt.googlesource.com/gwt 
# the final '7' might change in the meantime, check at https://gwt-review.googlesource.com/#/c/5351/
git fetch https://gwt.googlesource.com/gwt refs/changes/51/5351/7 && git checkout FETCH_HEAD
export GWT_VERSION=2.6.0-rc1 in order to be used as osgi export version
ant dist
cd maven
./push-gwt.sh

git clone https://github.com/cristcost/gwt-karaf-examples.git
mvn clean install
wget http://www.apache.org/dyn/closer.cgi?path=servicemix/servicemix-4/4.5.3/apache-servicemix-4.5.3.tar.gz
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






