# encoding=utf8
import sys
reload(sys)
sys.setdefaultencoding('utf8')

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
#for link in soup.find_all('a', href=re.compile(r'.*searchby=state*.')):
#	print urlparse.urljoin(url, link['href'])

data = {
'action':'get_breweries',
'_id':'United States',
'search_by':'country'
}

response = requests.post('https://www.brewersassociation.org/wp-admin/admin-ajax.php', data=data)

#print response.content

# this is just to save and analyze the xml response
#f = open('out.txt', 'w')
#print >> f, response.content
#f.close()

# open a file to save the brewery data
f = open('brewery.csv', 'w')
f.write("NAME, ADDRESS, MAP, TELEPHONE, TYPE, URL\n")

def write_to_csv(name, address1, address2, map, telephone, type, url):
	f.write('"' + name + '"' + "," + '"' + address1 +  address2 + '"' + "," + map + "," + telephone + "," + type + "," + url + "\n")

xml_data = response.content
xml_data = xml_data.replace('&', '&amp;')
#xml_data = xml_data.replace('>', '&gt;')
#xml_data = xml_data.replace('<', '&lt;')
#xml_data = xml_data.replace("'", '&apos')
#xml_data = xml_data.replace('"', '&quot;')
xml_data = xml_data.replace("O'Fallon", "O&quot;Fallon")
xml_data = xml_data.replace("John's", "John&quot;s")

# this is just to save and analyze the xml response
#f = open('out.txt', 'w')
#print >> f, xml_data
#f.close()

dom = minidom.parseString('<root>'+xml_data+'</root>')

nodes = dom.childNodes

for node in nodes[0].childNodes:
	name = ''
	address1 = ''
	address2 = ''
	telephone = ''
	type = ''
	url = ''
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
						#print attrs.items()
						#[(u'class', u'name')]
						#[(u'class', u'address')]
						#[(u'class', u'telephone')]
						#[(u'class', u'brewery_type')]
						#[(u'class', u'url')]
						if grandchild_node.hasAttribute('class') and attrs['class'].value == "name":
							lowest_nodes = grandchild_node.childNodes
							for lowest_node in lowest_nodes:
								if lowest_node.nodeValue is not None:
									name = lowest_node.nodeValue
								#print lowest_node.nodeValue
						if grandchild_node.hasAttribute('class') and attrs['class'].value == "address":
							lowest_nodes = grandchild_node.childNodes
							for lowest_node in lowest_nodes:
								if lowest_node.nodeValue is not None:
									address1 = lowest_node.nodeValue
								#print lowest_node.nodeValue
						if not grandchild_node.hasAttribute('class'):
							lowest_nodes = grandchild_node.childNodes
							for lowest_node in lowest_nodes:
								if lowest_node.nodeValue is not None:
									address2 = lowest_node.nodeValue
								#print lowest_node.nodeValue
								sub_lowest_nodes = lowest_node.childNodes
								#print sub_lowest_nodes
								for sub_lowest_node in sub_lowest_nodes:
									if sub_lowest_node.nodeValue is not None:
										map = sub_lowest_node.nodeValue
									#print sub_lowest_node.nodeValue # this just prints "Map"
						if grandchild_node.hasAttribute('class') and attrs['class'].value == "telephone":	
							lowest_nodes = grandchild_node.childNodes
							for lowest_node in lowest_nodes:
								if lowest_node.nodeValue is not None:
									telephone = lowest_node.nodeValue
								#print lowest_node.nodeValue
						if grandchild_node.hasAttribute('class') and attrs['class'].value == "brewery_type":
							lowest_nodes = grandchild_node.childNodes
							for lowest_node in lowest_nodes:
								if lowest_node.nodeValue is not None:
									type = lowest_node.nodeValue
								#print lowest_node.nodeValue
						if grandchild_node.hasAttribute('class') and attrs['class'].value == "url":
							lowest_nodes = grandchild_node.childNodes
							for lowest_node in lowest_nodes:
								sub_lowest_nodes = lowest_node.childNodes
								url = sub_lowest_nodes[0].nodeValue
								#print sub_lowest_nodes[0].nodeValue
					else:
						pass
						#print grandchild_node.nodeValue
				write_to_csv(name, address1, address2, str(map), telephone, type, str(url))
# Use BeautifulSoup to parse the response						
#soup = BeautifulSoup(response.content, "html.parser")
#print soup.prettify()

f.close()