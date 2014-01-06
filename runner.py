#!/usr/bin/env python
 
import socket
import google.protobuf.internal.decoder as decoder
import keyValue_pb2
 
HOST = "localhost"
INPORT = 8080
 
inSock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
inSock.connect((HOST, INPORT))
inSock.sendall("hello")

READING_BYTES = 10;
#outSock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
#outSock.connect((HOST, OUTPORT))
 
print "Python subprogram started"

while True:
    
    buf = inSock.recv(READING_BYTES)
    (size, position) = decoder._DecodeVarint(buf, 0)
    
    print '\n' + "size: "+str(size)+" position: "+str(position) + '\n'
    
    if size == 4294967295: # this is a hack my friend we probably need fixlength types for the length
		break;
    
    toRead = size+position-READING_BYTES;
    print toRead
    buf += inSock.recv(toRead) # this is probably inefficient because the buffer sizes changes all the time
    print("bufSize "+str(len(buf)))
    kv = keyValue_pb2.KeyValuePair();
    kv.ParseFromString(buf[position:position+size])
    print("key "+kv.key)
    print("value "+kv.value)
    
    #outBuf = kv.SerializeToString();
    #encoder._EncodeVarint(inSock, len(outBuf))
    #inSock.write(outBuf);
    
print "Got -1, Finishing python process"