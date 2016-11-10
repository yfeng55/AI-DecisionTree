import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



public class Main {

    public static int num_reviewers;
    public static int success_amount;
    public static int failure_amount;
    public static double S;
    public static double F;
    public static Reviewer[] reviewers;


    public static void main(String[] args) throws FileNotFoundException {

        getInput(args[0]);

        Node root = buildTree();
//        printTree(root);

        System.out.println();

        double overallmax = 0.0;
        Node suggestedchoice = null;
        for(Node choice : root.children){

            double localmax = maxExpectedValue(choice);

            if(localmax > overallmax){
                overallmax = localmax;
                suggestedchoice = choice;
            }
        }



        String advicestring;
        if(overallmax != 0.0 && suggestedchoice != null){
            advicestring = suggestedchoice.decisionstate;
        }else{
            advicestring = "Don't consult reviewers, reject book";
        }

        System.out.println("Expected value: " + overallmax);
        System.out.println(advicestring + ": ");


    }


    //        Expected value: 8540
    //        Consult reviewer 2: Yes.
    //        Publish


    public static double maxExpectedValue(Node root){

        double returnval = 0.0;

        root.visited = "seen";

        //base case (publish/reject node):
        if(root.children.isEmpty() && root.type.equals("publish")){
            double success_probability = S;
            if(!root.reviewers_used.isEmpty()){
                success_probability = Util.S_R(root.reviewers_used, root.probability, S, 'S');
            }
//            System.out.println(success_probability);

            //compute expected value
            double expectedval =  Util.expectedVal(success_probability, success_amount, (1-success_probability), failure_amount, root.reviewers_used);
//            System.out.println(expectedval + "\n");
            return expectedval;
        }
        else if(root.children.isEmpty() && root.type.equals("reject")){
//            System.out.println("reject cost:" + Util.rejectCost(root.reviewers_used) + "\n");
            return Util.rejectCost(root.reviewers_used);
        }


        ArrayList<Double> expectedvals = new ArrayList<>();

        for(Node n : root.children){

            if(n.visited.equals("unseen")){
                double expectedval = maxExpectedValue(n);
                expectedvals.add(expectedval);
            }
        }


        if(root.children.size() > 1){
            //case1: two consult nodes - return weighted avg
            if(root.children.get(0).type.equals("consult") || root.children.get(1).type.equals("consult")){
                returnval = root.children.get(0).probability * expectedvals.get(0) + root.children.get(1).probability * expectedvals.get(1);
            }
            //case2: decision node and publish node - return max value
            //case3: decision node and reject node - return max value
            else if(root.children.get(0).type.equals("decision") || root.children.get(1).type.equals("decision")){
                returnval = Math.max(expectedvals.get(0), expectedvals.get(1));
            }
        }else{
            return expectedvals.get(0);
        }



        root.visited = "finished";
        return returnval;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    public static Node buildTree(){

        //build root node:
        Node root = new Node("root", "root", 1.0);
        root.children.add(new Node("reject", "reject", 1.0));
        root.children.add(new Node("publish", "publish", 1.0));

        //create a set of reviewers
        ArrayList<Reviewer> reviewersleft = new ArrayList<>();
        for(Reviewer r : reviewers){
            reviewersleft.add(r);
        }

        //for each reviewer left in the set of reviewers, build a subtree
        for(int i=0; i<reviewersleft.size(); i++){

            //create a decision node for the reviewer
            Node decision = new Node("Consult Reviewer " + reviewersleft.get(i).id, "decision", 1.0);

            ArrayList<Reviewer> newreviewersleft = copyReviewers(reviewersleft);
            newreviewersleft.remove(i);

            //create a new decision node for each reviewer
            Node newnode1 = new Node("Consult R" + reviewersleft.get(i).id + ": True", "consult", reviewersleft.get(i).Rt);
            newnode1.reviewers_used.add(new Reviewer(reviewersleft.get(i), true) );

            Node yeschild = buildSubtree(newreviewersleft, newnode1, reviewersleft.get(i).Rt);
            decision.children.add(yeschild);

            //create a new decision node for each reviewer
            Node newnode2 = new Node("Consult R" + reviewersleft.get(i).id + ": False", "consult", reviewersleft.get(i).Rf);
            newnode2.reviewers_used.add(new Reviewer(reviewersleft.get(i), false) );

            Node nochild = buildSubtree(newreviewersleft, newnode2, reviewersleft.get(i).Rt);
            decision.children.add(nochild);

            root.children.add(decision);
        }

        return root;
    }


    public static Node buildSubtree(ArrayList<Reviewer> reviewersleft, Node current, double R_Rprev){

        //base cases:
        //if no more nodes to add
        if(reviewersleft.isEmpty() && current.reviewers_used.get(current.reviewers_used.size()-1).review == false){
            current.children.add(new Node("reject", "reject", 1.0-current.probability, copyReviewers(current.reviewers_used)));
            return current;
        }
        else if(reviewersleft.isEmpty() && current.reviewers_used.get(current.reviewers_used.size()-1).review == true){
            current.children.add(new Node("publish", "publish", current.probability, copyReviewers(current.reviewers_used)));
            return current;
        }
        //if node is an end node (publish or reject)
        if(current.type.equals("publish") || current.type.equals("reject") ){
            return current;
        }


        //recursive case:
        for(int i=0; i<reviewersleft.size(); i++){

            //calculate P(r=t) for this reviewer
            double r_probability = Util.R_R(current.reviewers_used, reviewersleft.get(i), R_Rprev, S);

            //create a new reviewerset where the current reviewer is removed
            ArrayList<Reviewer> new_reviewersleft = copyReviewers(reviewersleft);
            new_reviewersleft.remove(i);

            //create a decision node for this reviewer
            Node decision = new Node("Consult Reviewer " + reviewersleft.get(i).id, "decision", current.probability, copyReviewers(current.reviewers_used));

            //add a R=T node
            ArrayList<Reviewer> reviewers1 = copyReviewers(current.reviewers_used);
            reviewers1.add(new Reviewer(reviewersleft.get(i), true));
            Node child1 = buildSubtree(new_reviewersleft, new Node("Consult R" + reviewersleft.get(i).id + ": True", "consult", r_probability, reviewers1), r_probability);
            decision.children.add(child1);

            //add a R=F node
            ArrayList<Reviewer> reviewers2 = new ArrayList<>(current.reviewers_used);
            reviewers2.add(new Reviewer(reviewersleft.get(i), false));
            Node child2 = buildSubtree(new_reviewersleft, new Node("Consult R" + reviewersleft.get(i).id + ": False", "consult", 1-r_probability, reviewers2), 1-r_probability);
            decision.children.add(child2);


            current.children.add(decision);


            if(current.reviewers_used.get(current.reviewers_used.size()-1).review == false){
                current.children.add(new Node("reject", "reject", 1-current.probability, copyReviewers(current.reviewers_used)));
            }else{
                current.children.add(new Node("publish", "publish", current.probability, copyReviewers(current.reviewers_used)));
            }


        }

        return current;

    }


    ///// HELPER FUNCTIONS /////

    public static void printTree(Node root){

        System.out.println(root.toString());

        if(root.children.isEmpty()){ return; }

        for(Node n : root.children){
            printTree(n);
        }
    }


    public static void getInput(String inputpath) throws FileNotFoundException {

        Scanner s = new Scanner(new File(inputpath));

        //process first line
        String[] firstline = s.nextLine().split(" +");
        num_reviewers = Integer.parseInt(firstline[0]);
        success_amount = Integer.parseInt(firstline[1]);
        failure_amount = Integer.parseInt(firstline[2]);
        S = Double.parseDouble(firstline[3]);
        F = 1 - S;

        reviewers = new Reviewer[num_reviewers];

        //add reviewers to the reviewers list
        int i=1;
        while(s.hasNextLine()){
            String[] reviewerline = s.nextLine().split(" +");

            int cost = Integer.parseInt(reviewerline[0]);
            double Rt_St = Double.parseDouble(reviewerline[1]);
            double Rt_Sf = Double.parseDouble(reviewerline[2]);

            Reviewer reviewer = new Reviewer(i, cost, Rt_St, Rt_Sf, S);
            reviewers[i-1] = reviewer;
            i++;
        }

    }

    //copy a list of reviewers
    public static ArrayList<Reviewer> copyReviewers(ArrayList<Reviewer> reviewers){
        ArrayList<Reviewer> copy = new ArrayList<>(reviewers.size());
        for (Reviewer r : reviewers) {
            copy.add(new Reviewer(r));
        }
        return copy;
    }


}






