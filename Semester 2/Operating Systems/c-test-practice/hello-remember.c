#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct node {
    char* name;
    struct node* next;
};

struct node* add(struct node* head, char* name) {
    struct node* n;
    struct node* p;

    n = (struct node*)malloc(sizeof(struct node));
    n->name = (char*)malloc(strlen(name));
    strcpy(n->name, name);
    n->next = NULL;

    if(head == NULL) {
        return n;
    } 

    p = head;
    while(p->next != NULL) {
        p = p->next;
    }
    p->next = n;
    return head;
}

int known(struct node* head, char* name) {
    struct node* p;
    p = head;
    while(p != NULL && strcmp(p->name, name) != 0) {
        p = p->next;
    }
    return p != NULL;
}

void clear(struct node* head) {
    if(head == NULL) {
        return;
    }
    clear(head->next);
    free(head->name);
    free(head);
}

int main(int argc, char** argv) {
    char name[64];
    struct node* head = NULL;
    FILE* f;
    if(argc <= 1){
	printf("The file wasn't opened");
	return 0;
	}
    f = fopen(argv[1], "r");
    if(f == NULL){
	printf("The file wasn't opened");
	return 0;
	}
    while(fscanf(f, "%s", name)!= EOF) {
        if(known(head, name)) {
            printf("Still around %s, eh?\n", name);
        }
        else {
            head = add(head, name);
            printf("Hello %s\n", name);
        }
    }
    clear(head);
    fclose(f);
    return 0;
}
