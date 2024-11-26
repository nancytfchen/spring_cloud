An simple demo for flow control in 
  Spring boot + Spring cloud gateway + Redis
Implemented several flow control method.
1. RateLimter with Guava
2. AOP
3. Spring cloud gateway + Redis

Also provided 2 test case to test High Concurrent Request under flow control
1.  HighConcurrentRequestTest - is for test api1 with flow control.
2.  MultipleAPIFlowControlTest - is for test api1,2,3 in the same time with same flow control.
