import matplotlib
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.patches as mpatches

def main () :
    f = open("2d_small.txt", "r")
    xs = [0]
    ys = [0]
    fig,ax = plt.subplots(1)
    for line in f :
        print("line-"+line+"-")
        s = line.rstrip().split(" ")
        print(s)
        # print("x-"+s[0]+"-")
        score = float(s[1])
        size = float(s[0])
        print("("+str(size)+" "+str(score)+")")
        xs.append(size)
        ys.append(score)
        plt.scatter(size, score, s=25, c="blue", alpha=0.5)
    # print("xs" +str(xs))
    # print("ys" + str(ys))

    # plt.plot(xs, ys, 'b')
    plt.axis([0, 1, 0, 1]) # [xmin, xmax, ymin, ymax]
    # plt.ylabel("Accuracy %")
    # plt.xlabel("Training Set Size")
    # plt.grid(True)

    plt.suptitle('Graph Title')

    # plt.xticks(np.arange(min(xs), max(xs)+1, 50))
    # plt.yticks(np.arange(0, 101, 10))
    plt.show()
    print("what?")

if __name__ == "__main__" :
    main()
