import time
import os

PADDING = 4
SLEEP_TIME = 60


def print_proc(name):
    st = f"{name} {os.getpid()}"
    extra_padding = 0

    if name == "B":
        extra_padding = 1
    elif name in ("C", "D"):
        extra_padding = 2
    elif name in ("E", "F", "G"):
        extra_padding = 3
    elif name in ("H", "I"):
        extra_padding = 4

    print(st.rjust(extra_padding*PADDING+len(st)))


def create_leaf(name):
    print_proc(name)
    time.sleep(SLEEP_TIME)


if __name__ == "__main__":
    try:
        print_proc("A")

        new_proc = os.fork()
        if new_proc != 0:
            # Seguimos en el padre (A)
            os.wait()  # Que espere a los hijos
        else:
            # Estamos en el hijo (B)
            print_proc("B")

            new_proc = os.fork()
            if new_proc == 0:
                # Estamos en el 1er hijo (C)
                print_proc("C")

                new_proc = os.fork()
                if new_proc != 0:
                    # Seguimos en el padre (C)
                    os.wait()
                else:
                    # Estamos en el hijo (E)
                    print_proc("E")

                    new_proc = os.fork()
                    if new_proc == 0:
                        create_leaf("H")
                    else:
                        new_proc = os.fork()

                        if new_proc != 0:
                            os.wait()
                        else:
                            create_leaf("I")
            else:
                # Seguimos en el padre (B)
                new_proc = os.fork()
                if new_proc != 0:
                    # Seguimos en el padre (B)
                    os.wait()
                else:
                    # Estamos en el 2do hijo (D)
                    print_proc("D")

                    new_proc = os.fork()
                    if new_proc == 0:
                        create_leaf("F")
                    else:
                        new_proc = os.fork()
                        if new_proc != 0:
                            os.wait()
                        else:
                            create_leaf("G")

    except Exception as e:
        print(e)
