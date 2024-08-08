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
import { AuthorizationService } from '../../../auth-callback/authorization.service';
import { Role } from '../../utils';
import { CommonModule } from '@angular/common';

interface NavigationNode {
  name: string;
  link: string;
  children?: NavigationNode[];
  permission: boolean;
}

/** Flat node with expandable and level information */
interface ExampleFlatNode {
  expandable: boolean;
  name: string;
  level: number;
  link: string;
  permission: boolean;
  children?: NavigationNode[];
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
    CommonModule,
  ],
  templateUrl: './toolbar-menu.component.html',
  styleUrl: './toolbar-menu.component.scss',
})
export class ToolbarMenuComponent {
  isAdminOrTeacher: boolean = false;
  isStudent: boolean = false;
  TREE_DATA: NavigationNode[] = [];
  private _transformer = (node: NavigationNode, level: number) => {
    return {
      expandable: !!node.children && node.children.length > 0,
      name: node.name,
      level: level,
      link: node.link,
      permission: node.permission,
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

  constructor(
    public route: ActivatedRoute,
    router: Router,
    private authorizationService: AuthorizationService
  ) {
    this.initializeTreeData();
  }

  private async initializeTreeData(): Promise<void> {
    this.isAdminOrTeacher = await this.authorizationService.checkRoles([
      Role.ADMIN,
      Role.TEACHER,
    ]);
    this.isStudent = await this.authorizationService.checkRoles([Role.STUDENT]);

    this.TREE_DATA = [
      {
        name: 'Course',
        link: 'course',
        children: [
          {
            name: 'Enrolment',
            link: 'course/enrolment',
            permission: this.isAdminOrTeacher,
          },
        ],
        permission: this.isAdminOrTeacher,
      },
      {
        name: 'Assessment',
        link: 'assessment',
        permission: this.isAdminOrTeacher || this.isStudent,
      },
    ];
    this.dataSource.data = this.TREE_DATA;
  }

  hasChild = (_: number, node: ExampleFlatNode) => node.expandable;

  getFullLink(node: NavigationNode): string[] {
    const fullPath = this.buildPath(node, this.TREE_DATA, []);
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
