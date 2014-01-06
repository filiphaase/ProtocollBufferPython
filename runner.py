#!/usr/bin/env python
 
import socket
 
HOST = "localhost"
INPORT = 8080
 
inSock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
inSock.connect((HOST, INPORT))
inSock.sendall("hello")
#outSock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
#outSock.connect((HOST, OUTPORT))
 
print "Python subprogram started"

while True:
    data = inSock.recv(4)
    print "1 got size: '" + data + "'" 
    
    bit_string = 1
    for i in xrange(1,32):
        print str(data & bit_string)
        bit_string >> 1 
        
    if data == -1:
        break
    
    data = sock.recv(data)
    print "2 got data: " + data
    kv = KeyValue_pb2.KeyValue();
    kv.ParseFromString(buf[position:position+size])
    buf = sock.recv(data)
