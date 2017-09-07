import urllib2
from bs4 import BeautifulSoup

breweries = "https://www.brewersassociation.org/directories/breweries/"
page = urllib2.urlopen(breweries)
soup = BeautifulSoup(page)
print soup.prettify()