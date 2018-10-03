import matplotlib
import matplotlib.pyplot as plt

def main () :
    f = open("temp1.txt", "r")
    xs = [0]
    ys = [0]
    max = 0
    for line in f :
        # print("line-"+line+"-")
        s = line.split(" ")
        # print("x-"+s[0]+"-")
        # xs.append(float(s[0]))
        t = float(s[1].split("\n")[0])
        if(t > max):
            max = t

        # ys.append(t)
        plt.scatter(float(s[0]), t, s=2, c="blue", alpha=0.5)
    print("xs" +str(xs))
    print("ys" + str(ys))
    print(max)

    # plt.plot(xs, ys, 'b')
    plt.axis([0, max+5, 0, 105])
    plt.show()
    print("show?")

if __name__ == "__main__" :
    main()
