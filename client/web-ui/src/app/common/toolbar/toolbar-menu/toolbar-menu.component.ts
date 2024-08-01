import { FlatTreeControl } from '@angular/cdk/tree';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import {
  MatTreeFlatDataSource,
  MatTreeFlattener,
  MatTreeModule,
} from '@angular/material/tree';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';

interface NavigationNode {
  name: string;
  link: string;
  children?: NavigationNode[];
}

const TREE_DATA: NavigationNode[] = [
  {
    name: 'Course',
    link: 'course',
    children: [{ name: 'Enrolment', link: 'course/enrolment' }],
  },
  {
    name: 'Assessment',
    link: 'assessment',
    children: [{ name: 'Question', link: 'assessment/question' }],
  },
];

/** Flat node with expandable and level information */
interface ExampleFlatNode {
  expandable: boolean;
  name: string;
  level: number;
  link: string;
}

@Component({
  selector: 'app-toolbar-menu',
  standalone: true,
  imports: [
    RouterModule,
    MatListModule,
    MatIconModule,
    MatTreeModule,
    MatButtonModule,
  ],
  templateUrl: './toolbar-menu.component.html',
  styleUrl: './toolbar-menu.component.scss',
})
export class ToolbarMenuComponent {
  private _transformer = (node: NavigationNode, level: number) => {
    return {
      expandable: !!node.children && node.children.length > 0,
      name: node.name,
      level: level,
      link: node.link,
    };
  };

  treeControl = new FlatTreeControl<ExampleFlatNode>(
    (node) => node.level,
    (node) => node.expandable
  );

  treeFlattener = new MatTreeFlattener(
    this._transformer,
    (node) => node.level,
    (node) => node.expandable,
    (node) => node.children
  );

  dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);

  constructor(public route: ActivatedRoute, router: Router) {
    this.dataSource.data = TREE_DATA;
  }

  hasChild = (_: number, node: ExampleFlatNode) => node.expandable;

  getFullLink(node: NavigationNode): string[] {
    const fullPath = this.buildPath(node, TREE_DATA, []);
    return fullPath;
  }

  private buildPath(
    node: NavigationNode,
    treeData: NavigationNode[],
    path: string[]
  ): string[] {
    for (const parentNode of treeData) {
      if (parentNode === node) {
        return [...path, parentNode.link];
      }
      if (parentNode.children) {
        const childPath = this.buildPath(node, parentNode.children, [
          ...path,
          parentNode.link,
        ]);
        if (childPath.length) {
          return childPath;
        }
      }
    }
    return [];
  }
}
