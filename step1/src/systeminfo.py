from subprocess import Popen, PIPE

res = Popen(['wmic', 'qfe', 'list', 'brief', '/format:texttablewsys'], stdout=PIPE)
output = res.communicate()[0]
print output