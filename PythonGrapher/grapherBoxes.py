import matplotlib
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.patches as mpatches

def main () :
    f = open("2d_small.txt", "r")
    fig,ax = plt.subplots(1)
    colors = ["black", "red", "green", "purple","blue"]
    i = 0
    for line in f :
        # print("line-"+line+"-")
        s = line.rstrip().split(" ")
        if (len(s) == 2) :
            # print(s)
            score = float(s[1])
            size = float(s[0])
            # print("("+str(size)+" "+str(score)+")")
            plt.scatter(size, score, s=25, c="blue", alpha=0.5)
        elif(len(s) == 4) :
            print("size 4 box")
            x = float(s[0])
            y = float(s[1])
            w = float(s[2])
            h = float(s[3])
            rect = mpatches.Rectangle([x, y], w, h, ec=colors[i%5], fc="none")
            ax.add_patch(rect)
            i += 1
        else :
            print("bad size")

    plt.axis([0, 1, 0, 1]) # [xmin, xmax, ymin, ymax]
    plt.scatter(.5, .5, s=25, c="red", alpha=0.5)
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
