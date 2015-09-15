import csv

def get_justices(text):
	first = text.find('{{SCOTUS-termlist-start')
	last = text.find('}}')
	return text[first+24:last].split('|')

def get_case_name(temp):
	first = temp.find('case')
	last = temp[first:].find('\n')
	return temp[first+5:first+last]

def get_case_text(temp):
	case_list = []
	while(temp.find('{{SCOTUS-termlist-entry') != -1):
		first = temp.find('{{SCOTUS-termlist-entry')
		last = temp[first:].find('}}')
		case_list.append(temp[first+30:first+last])
		temp = temp[first+last:]
	return case_list

def get_opinions(case):
	opinions_list = []
	while(case.find('opinion1=') != -1):
		##find opinion
		first = case.find('opinion1=')
		last = case[first:].find(' ')
		opinion = case[first+9:first+last]
		case = case[first:]
		##find justice
		first2 = case.find('--')
		last2 = case[first2+2:].find('--')
		justice = case[first2+2:first2+last2+2]
		case = case[first2+last2:]
		## update
		opinions_list.append([justice, opinion])
	return opinions_list

f = open('2014Cases.txt', 'r')
text = f.read()
case_list = get_case_text(text)

with open('2014CasesTrial.csv', 'wb') as csvfile:
	fieldnames = ['names'] + map(lambda x:x.lower(), get_justices(text))
	writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
	writer.writeheader()
	for case in case_list:
		judge_dict = {'names':get_case_name(case)}
		for opinion in get_opinions(case):
			if(opinion[1].find('dissent') != -1):
				val = 0
			elif(opinion[1].find('concurrence') != -1):
				val = 0.5
			elif(opinion[1].find('majority') != -1):
				val = 1
			judge_dict[opinion[0]] = val
		writer.writerow(judge_dict)