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

    # plt.plot(xs, ys, 'b')
    plt.axis([0, 1, 0, 1]) # [xmin, xmax, ymin, ymax]
    # plt.ylabel("Accuracy %")
    # plt.xlabel("Training Set Size")
    # plt.grid(True)

    plt.suptitle('Graph Title')
    rect = mpatches.Rectangle([0.122229, 0.104104],  0.672031 - 0.122229,  0.696483 - 0.104104, ec="black", fc="none")
    ax.add_patch(rect)
    rect2 = mpatches.Rectangle([0.374093, 0.330244], 0.672031-0.374093, 0.651324-0.330244, ec="blue", fc="none")
    ax.add_patch(rect2)

    # plt.xticks(np.arange(min(xs), max(xs)+1, 50))
    # plt.yticks(np.arange(0, 101, 10))
    plt.show()
    print("what?")

if __name__ == "__main__" :
    main()
