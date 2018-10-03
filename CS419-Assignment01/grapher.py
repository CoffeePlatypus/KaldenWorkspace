import matplotlib
import numpy as np
import matplotlib.pyplot as plt

def main () :
    f = open("temp2.txt", "r")
    xs = [0]
    ys = [0]
    for line in f :
        print("line-"+line+"-")
        s = line.split(" ")
        # print("x-"+s[0]+"-")
        score = float(s[1].split("\n")[0])
        size = float(s[0])
        print("("+str(size)+" "+str(score)+")")
        xs.append(size)
        ys.append(score)
        plt.scatter(size, score, s=25, c="blue", alpha=0.5)
    # print("xs" +str(xs))
    # print("ys" + str(ys))

    plt.plot(xs, ys, 'b')
    plt.axis([0, 510, 0, 100]) # [xmin, xmax, ymin, ymax]
    plt.ylabel("Accuracy %")
    plt.xlabel("Training Set Size")
    # plt.grid(True)

    plt.suptitle('Performance with Training Size 500 and Increment 10')
    plt.xticks(np.arange(min(xs), max(xs)+1, 50))
    plt.yticks(np.arange(0, 101, 10))
    plt.show()
    print("what?")

if __name__ == "__main__" :
    main()
