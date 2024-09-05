import subprocess

def run_test(key, expected_output):
    
    result = subprocess.run(
        ["./main"],
        input=key,
        text=True,
        capture_output=True
    )
    if result.returncode == 0:
        if result.stdout != expected_output:
            print(f"Fail: expected '{expected_output}' but got '{result.stdout}'")
        else:
            print(f"Success: expected '{expected_output}' and got '{result.stdout}'")
    else:
        if result.stderr != expected_output:
            print(f"Fail: expected '{expected_output}' but got '{result.stderr}'")
        else:
            print(f"Success: expected '{expected_output}' and got '{result.stderr}'")


diction = {
    'first': 'first word explanation',
    'second': 'second word explanation',
    'third': 'third word explanation',
    'non exiscting': 'Error: key wasn\'t found in dict'
}

if __name__ == '__main__':
    
    for key, value in diction.items():
        run_test(key, value)

