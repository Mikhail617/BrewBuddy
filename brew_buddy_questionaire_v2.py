import csv
import time

LOOKING_TO_GET_DRUNK = False
DRINKING_SOCIALLY = False
GREATER_THAN_5_PERCENT_ABV = False
PAIR_WITH_FOOD_OR_DESERT = False
LIGHTER = False
DARKER = False

foods_options = {1 : "Lighter Foods (chicken, salads, salmon)",
				 2 : "Lighter Foods (seafood, chicken, cheese)",
				 3 : "Lighter Foods (sushi, seafood, Mexican, Fruit)",
				 4 : "Strong Spicy Foods (burgers, steak, Indian, Thai, carrot cake)",
				 5 : "Strong Spicy Foods (burgers, pizza, sandwiches)",
				 6 : "Strong Foods (beef, port, BBQ, Pound Cake)",
				 7 : "Hearty Foods (pork, grilled salmon, duck, nut-based deserts)",
				 8 : "Roasted or Smoked Foods (BBQ, blackened fish, chocolate)",
				 9 : "Savory Foods or Chocolate Deserts (Fois Gras, Steak, Lamb, Oysters, BBQ Ribs)"}
				 
lighter_flavor_options = {1 : "Bitterness: mild, Flavor: mild with a crisp finish",
						  2 : "Bitterness: mild to moderate, Flavor: mild to malt sweetness",
						  3 : "Bitterness: mild, Flavor: fruity and spicy",
						  4 : "Bitterness: moderate to high, Flavor: intense, spicy, earthy or aromatic",
						  5 : "Bitterness: moderate, Flavor: caramel richness"}

darker_flavor_options = {1 : "Bitterness: moderate, Flavor: malt caramel sweetness",
						 2 : "Bitterness: mild to moderate, Flavor: malt flavors of caramel, nuts, biscuit and toffee",
						 3 : "Bitterness: mild to moderate, Flavor: roasted malt flavors with hints of chocolate",
						 4 : "Bitterness: moderate to high, Flavor: heavily roasted malt flavors with hints of chocolate"}

lighter_beers = {1 : "Pale Larger/Pilsner",
				 2 : "Blonde Ale",
				 3 : "Hefeweizen",
				 4 : "Pale Ale/IPA",
				 5 : "Amber Ale"}
				 
darker_beers = {1 : "Irish Red Ale",
				2 : "Brown Ale",
				3 : "Porter",
				4 : "Stout"}

def search_db_by_type(types):
	with open('BrewBuddy_sample_data.xlsx - Brews.csv', 'rb') as csvfile:
		brew_list_reader = csv.DictReader(csvfile)
		for row in brew_list_reader:
			for type in types:
				if type in row['Name'] or type in row['Type']:
					print row['Name'] + ', ' + row['Brewery']
		
def questions():
	global LIGHTER
	global DARKER
	global PAIR_WITH_FOOD_OR_DESERT
	print("Are you looking to get drunk or just drinking socially? ")
	ans = raw_input("\n1. Get Drunk! or 2. Drink Socially? ")
	print("\nAre you looking for lighter or darker beer styles? ")
	ans = raw_input("\n1. Lighter or 2. Darker? ")
	if ans is "1":
		LIGHTER = True
	elif ans is "2":
		DARKER = True
	print("\nAre you looking to pair your beer with food or desert? ")
	ans = raw_input("\n1. Yes or 2. No? ")
	
	print("")
	if ans is "1":
		PAIR_WITH_FOOD_OR_DESERT = True
		for key, value in foods_options.items():
			print(str(key) + ". " + value)
	else:
		if LIGHTER:
			for key, value in lighter_flavor_options.items():
				print(str(key) + ". " + value)
		elif DARKER:
			for key, value in darker_flavor_options.items():
				print(str(key) + ". " + value)	

	ans = raw_input("\nPlease select the closest match: ")
	
	print("")
	
	types = []

	if PAIR_WITH_FOOD_OR_DESERT is True:
		if ans is "1":
			print("Based on your answers your brew type is Pale Larger/Pilsner\n")
			types.append("Pale Larger")
			types.append("PALE LARGER")
			types.append("Pilsner")
			types.append("PILSNER")
			types.append("Pilzner")
			types.append("PILZNER")
		elif ans is "2":
			print("Based on your answers your brew type is Blonde Ale\n")
			types.append("Blonde Ale")
			types.append("BLONDE ALE")
		elif ans is "3":
			print("Based on your answers your brew type is Hefeweizen\n")
			types.append("Hefeweizen")
			types.append("HEFEWEIZEN")
			types.append("Weizenbier")
			types.append("WEIZENBIER")
			types.append("Wheat")
			types.append("WHEAT")
		elif ans is "4":
			print("Based on your answers your brew type is Pale Ale/IPA\n")
			types.append("Pale Ale")
			types.append("PALE ALE")
			types.append("IPA")
		elif ans is "5":
			print("Based on your answers your brew type is Amber Ale\n")
			types.append("Amber Ale")
			types.append("AMBER ALE")
		elif ans is "6":
			print("Based on your answers your brew type is Irish Red Ale\n")
			types.append("Irish Red Ale")
			types.append("IRISH RED ALE")
		elif ans is "7":
			print("Based on your answers your brew type is Brown Ale\n")
			types.append("Brown Ale")
			types.append("BROWN ALE")
		elif ans is "8":
			print("Based on your answers your brew type is Porter\n")
			types.append("Porter")
			types.append("PORTER")			
		elif ans is "9":
			print("Based on your answers your brew type is Stout\n")
			types.append("Stout")
			types.append("STOUT")
		else:
			print("Invalid option.\n")
	else:
		if LIGHTER is True:
			if ans is "1":
				print("Based on your answers your brew type is Pale Larger/Pilsner\n")
				types.append("Pale Larger")
				types.append("PALE LARGER")
				types.append("Pilsner")
				types.append("PILSNER")
				types.append("Pilzner")
				types.append("PILZNER")
			elif ans is "2":
				print("Based on your answers your brew type is Blonde Ale\n")
				types.append("Blonde Ale")
				types.append("BLONDE ALE")
			elif ans is "3":
				print("Based on your answers your brew type is Hefeweizen\n")
				types.append("Hefeweizen")
				types.append("HEFEWEIZEN")
			elif ans is "4":
				print("Based on your answers your brew type is Pale Ale/IPA\n")
				types.append("Pale Ale")
				types.append("PALE ALE")
				types.append("IPA")
			elif ans is "5":
				print("Based on your answers your brew type is Amber Ale\n")
				types.append("Amber Ale")
				types.append("AMBER ALE")
		elif DARKER is True:
			if ans is "1":
				print("Based on your answers your brew type is Irish Red Ale\n")
				types.append("Irish Red Ale")
				types.append("IRISH RED ALE")
			elif ans is "2":
				print("Based on your answers your brew type is Brown Ale\n")
				types.append("Brown Ale")
				types.append("BROWN ALE")
			elif ans is "3":
				print("Based on your answers your brew type is Porter\n")
				types.append("Porter")
				types.append("PORTER")			
			elif ans is "4":
				print("Based on your answers your brew type is Stout\n")
				types.append("Stout")
				types.append("STOUT")
		else:
			print("Please select either 'Lighter' or 'Darker' brew.")
			
	print("Searching the BrewBuddy database for craft beers in your area that match your criteria...\n")
	
	time.sleep(2)

	search_db_by_type(types)	
	
	#print("Lighter beers: ")
	#for key, value in lighter_beers.items():
	#	print(str(key) + ". " + value)
	#print("")
	#print("Darker beers: ")
	#for key, value in darker_beers.items():
	#	print(str(key) + ". " + value)
		
def art():
	print("")
	print("")
	print("")
	print("	WELCOME TO THE BREWBUDDY SERVER!")
	print("")
	print("")
	print("")
	print("                   .sssssssss.")
	print("              .sssssssssssssssssss")
	print("            sssssssssssssssssssssssss")
	print("           ssssssssssssssssssssssssssss")
	print("            @@sssssssssssssssssssssss@ss")
	print("            |s@@@@sssssssssssssss@@@@s|s")
	print("     _______|sssss@@@@@sssss@@@@@sssss|s")
	print("   /         sssssssss@sssss@sssssssss|s")
	print("  /  .------+.ssssssss@sssss@ssssssss.|")
	print(" /  /       |...sssssss@sss@sssssss...|")
	print("|  |        |.......sss@sss@ssss......|")
	print("|  |        |..........s@ss@sss.......|")
	print("|  |        |...........@ss@..........|")
	print(" \  \       |............ss@..........|")
	print("  \  '------+...........ss@...........|")
	print("   \________ .........................|")
	print("            |.........................|")
	print("           /...........................\\")
	print("          |.............................|")
	print("             |.......................|")
	print("                 |...............|")	
	print("")
	print("")
	print("")

art()	
questions()
#search_db_by_type()
