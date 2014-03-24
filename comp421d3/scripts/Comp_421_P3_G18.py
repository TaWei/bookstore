#!/usr/bin/python
import subprocess  

#Before running this script, make sure you run create.clp and insert.clp.
def executeProgram():
    #Add user
    proc = subprocess.Popen(['java', '-jar', 'Comp_421_P3_G18.jar'], stdin=subprocess.PIPE)
    proc.communicate('1\nkishani\nkishan\n\patel\n123fakeemail@fakeschool.com\nhaha\n8\n')

    #Add book
    proc = subprocess.Popen(['java', '-jar', 'Comp_421_P3_G18.jar'], stdin=subprocess.PIPE)
    proc.communicate('2\n911-411\nthe-ultimate-joke-book\nit-has-a-lot-of-jokes\nhumor\n500\n8\n')

    #Get books ordered by user that are greater than N stars
    proc = subprocess.Popen(['java', '-jar', 'Comp_421_P3_G18.jar'], stdin=subprocess.PIPE)
    proc.communicate('3\n1\n4\n8\n')

    #Get N best customers for the year
    proc = subprocess.Popen(['java', '-jar', 'Comp_421_P3_G18.jar'], stdin=subprocess.PIPE)
    proc.communicate('4\n10\n2014\n8\n')

    #Get N top genres for the year
    proc = subprocess.Popen(['java', '-jar', 'Comp_421_P3_G18.jar'], stdin=subprocess.PIPE)
    proc.communicate('5\n10\n2014\n8\n')

    #Change coupon expiration date
    proc = subprocess.Popen(['java', '-jar', 'Comp_421_P3_G18.jar'], stdin=subprocess.PIPE)
    proc.communicate('6\n1\n2015\n12\n31\n11:59:59\n8\n')

    #Change the delivery address for a particular user
    proc = subprocess.Popen(['java', '-jar', 'Comp_421_P3_G18.jar'], stdin=subprocess.PIPE)
    proc.communicate('7\n1\n1\n1\n8\n')

    
if __name__ == '__main__':
    executeProgram()
