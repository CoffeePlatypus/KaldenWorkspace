import matplotlib
import numpy as np
import matplotlib.pyplot as plt

def main () :
    np.random.seed(19680801)
    f = open("temp1.txt", "r")
    xs = [0]
    ys = [0]
    for line in f :
        # print("line-"+line+"-")
        s = line.split(" ")
        # print("x-"+s[0]+"-")
        score = float(s[1].split("\n")[0])
        size = float(s[0])
        print(str(size)+" "+str(score))
        xs.append(size)
        ys.append(score)
        plt.scatter(size, score, s=25, c="blue", alpha=0.5)
    print("xs" +str(xs))
    print("ys" + str(ys))

    plt.plot(xs, ys, 'b')
    plt.axis([0, 1010, 0, 100]) # [xmin, xmax, ymin, ymax]
    plt.show()
    print("what?")

if __name__ == "__main__" :
    main()
