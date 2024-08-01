import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { IBreadCrumb } from './bread-crumb.component';
import { ActivatedRoute, Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class BreadcrumbService {
  private breadcrumbsSubject = new BehaviorSubject<IBreadCrumb[]>([]);
  public breadcrumbs$ = this.breadcrumbsSubject.asObservable();

  constructor(private router: Router) {}

  setBreadcrumbs(breadcrumbs: IBreadCrumb[]) {
    this.breadcrumbsSubject.next(breadcrumbs);
  }

  public createBreadcrumbs(
    route: ActivatedRoute,
    url: string = '',
    breadcrumbs: IBreadCrumb[] = []
  ): IBreadCrumb[] {
    const children: ActivatedRoute[] = route.children;

    if (children.length === 0) {
      return breadcrumbs;
    }

    for (const child of children) {
      const routeURL: string = child.snapshot.url
        .map((segment) => segment.path)
        .join('/');
      if (routeURL !== '') {
        url += `/${routeURL}`;
        breadcrumbs.push({ label: routeURL, url });
      }
      return this.createBreadcrumbs(child, url, breadcrumbs);
    }
    return breadcrumbs;
  }

  public updateBreadcrumbs(): void {
    const breadcrumbs: IBreadCrumb[] = this.createBreadcrumbs(
      this.router.routerState.root
    );
    this.setBreadcrumbs(breadcrumbs);
  }
}
