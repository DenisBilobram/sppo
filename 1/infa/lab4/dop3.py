from datetime import datetime
import time
from main import main
from dop1 import dop1_main
from dop2 import dop2_main

# main time
start_time = datetime.now()

for i in range(100):
    main()

print(f"Main time: {datetime.now() - start_time}")

# dop1 time
start_time = datetime.now()

for i in range(100):
    dop1_main()

print(f"Dop1 time: {datetime.now() - start_time}")

# dop2 time
start_time = datetime.now()

for i in range(100):
    dop2_main()

print(f"Dop2 time: {datetime.now() - start_time}")
