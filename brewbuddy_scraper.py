from bs4 import BeautifulSoup
import requests
import re
import urllib2
import urlparse
from xml.dom import minidom

#url = raw_input("Enter a website to extract the URL's from: ")
url = "www.brewersassociation.org/directories/breweries"
r  = requests.get("http://" +url)
data = r.text
soup = BeautifulSoup(data, "html.parser")
# get all the html
#print soup.prettify()

# get all the different state search result links
for link in soup.find_all('a', href=re.compile(r'.*searchby=state*.')):
	print urlparse.urljoin(url, link['href'])

data = {
'action':'get_breweries',
'_id':'United States',
'search_by':'country'
}

response = requests.post('https://www.brewersassociation.org/wp-admin/admin-ajax.php', data=data)

#print response.content

#f = open('out.txt', 'w')
#print >> f, response.content
#f.close()

xml_data = response.content
xml_data = xml_data.replace('&', '&amp;')
#xml_data = xml_data.replace('>', '&gt;')
#xml_data = xml_data.replace('<', '&lt;')
#xml_data = xml_data.replace("'", '&apos')
#xml_data = xml_data.replace('"', '&quot;')
xml_data = xml_data.replace("O'Fallon", "O&quot;Fallon")
xml_data = xml_data.replace("John's", "John&quot;s")

#f = open('out.txt', 'w')
#print >> f, xml_data
#f.close()

dom = minidom.parseString('<root>'+xml_data+'</root>')

nodes = dom.childNodes

for node in nodes[0].childNodes:
	child_nodes = node.childNodes
	for child_node in child_nodes:
		#print child_node
		attrs = child_node.attributes
		#print attrs
		if attrs is not None:
			#print attrs.items()
			if child_node.hasAttribute("class") and attrs['class'].value == "vcard simple brewery-info":
				grandchild_nodes = child_node.childNodes
				for grandchild_node in grandchild_nodes:
					attrs = grandchild_node.attributes
					if attrs is not None:
						print attrs.items()
	#attrs = node.attributes
	#print node
	#print attrs
	#print attrs.keys()
	#print attrs['class'].value