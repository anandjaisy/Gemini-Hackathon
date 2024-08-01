import { Component, Input, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { BreadcrumbService } from './breadcrumb.service';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

export interface IBreadCrumb {
  label: string;
  url: string;
}

@Component({
  selector: 'fetebird-bread-crumb',
  standalone: true,
  imports: [MatIconModule, RouterModule, CommonModule],
  templateUrl: './bread-crumb.component.html',
  styleUrl: './bread-crumb.component.scss',
})
export class BreadCrumbComponent implements OnInit {
  breadcrumbs: IBreadCrumb[] = [];
  constructor(private breadcrumbService: BreadcrumbService) {}
  ngOnInit(): void {
    this.breadcrumbService.breadcrumbs$.subscribe((breadcrumbs) => {
      this.breadcrumbs = breadcrumbs;
    });
  }
}
