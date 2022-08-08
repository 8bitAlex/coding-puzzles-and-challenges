# Alex Salerno's Portfolio
[![Test & Build](https://github.com/8bitAlex/alex-salerno-portfolio/actions/workflows/gradle.yml/badge.svg)](https://github.com/8bitAlex/alex-salerno-portfolio/actions/workflows/gradle.yml)

Hello and welcome to my little corner of the internet. 
My name is Alex Salerno, and I am a professional software engineer.
I've put together for you an accumulation of work I have done including coding problems, challenges, and examples.

In this portfolio, you will find both executable examples and references to code outside this repo.
The examples range vastly in complexity and difficulty. 
I have done my best to document each example so that the intentions are clear.

While I do my absolute best to maintain production ready code it is inevitable that there will be typos and/or issues. 
That being said, this is a living repo and I may update or change it at any time.

Thank you for your interest in my work and I hope you find some insight and maybe a bit of fun.

You can find more information on my [Personal Website](https://www.alexsalerno.com/) and [LinkedIn](https://www.linkedin.com/in/8bitalex/).

# Repo Information
This project is primarily written in **Java 17** and built with **Gradle**.

If you would like to run the executable code I recommend downloading [IntelliJ Idea](https://www.jetbrains.com/idea/) and importing the project.

At a minimum you will find JUnit tests in the `test` folder for each example that can be run. 
If there are additional ways to run the code, such as an executable (like UI or CLI), it will be documented under the associated section.

# Problem Sets & Examples
## Singly Linked List with a random reference to another Node
### Breakdown
**Prompt:** Given a singly linked list that contains a reference to a random node within the same list, output a 
duplicate copy that has no dependencies on the original list. Your algorithm should be as time and space constrained as possible.
The output list must follow normal linked list semantics – in particular, nodes must be individually allocated. 
Please note, tag should only be used to output sufficient evidence that the initial list and its copy are valid.

**Summary:** This one was a fun warm-up exercise because it takes a common data structure and puts a twist on it.
There are a few challenges here: 1) create a list with a reference to a random node within space constraints 2) 
optimally duplicate the list and the random references 3) bonus: make it thread safe

The creation of the list is pretty straight forward. The first pass creates a Node for each tag. 
The second pass then assigns each node a reference to a random node in the list. There is a bit of efficiency gained
in the second pass because the length of the list is cached from the first pass, so it is only processed once per call.
This deconstructs to worst case _O(N+N<sup>2</sup>)_.

The list duplication uses recursion to iterate on each item. For each node in the original list, a copy is created, 
cached in a `HashMap`, and added to the new list. A Java specific problem can occur here. Java does not optimize its recursion, 
even if tail recursion is used. This means that for each recursive call a stack frame is pushed to the system stack.
If there are a high number nodes in the list this can cause a `StackOverflowError`. In order to avoid this problem, the
recursion method is limited to a specific number of calls. The current node reference is saved and if the recursive depth 
is greater than the `MAX_COPY_DEPTH` it steps out of the current stack and restarts the recursion.
```
do {
    deepCopy(currentNode, 1);
} while(currentNode.next != null);
```
```
private static Node deepCopy(final Node node, final int depth) {
    currentNode = node; // cache current location in list
    if(currentNode == null || depth > MAX_COPY_DEPTH) return null;
...
```

Once each Node is duplicated, each Node is then re-assigned its random reference by looking up the referenced Node in the copy cache.
This deconstructs to worst case _O(2N)_.

The list is thread safe by mutex locking the cachable resources with a `ReentrantLock`.

### Code
- [RandomLinkedList](/portfolio-core/src/main/java/org/salerno/datastructure/RandomLinkedList.java)
- [RandomLinkedListTest](/portfolio-core/src/test/java/org/salerno/datastructure/RandomLinkedListTest.java)