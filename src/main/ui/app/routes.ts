import {
  type RouteConfig,
  index,
  layout,
  route,
} from "@react-router/dev/routes";

export default [
  index("routes/home.tsx"),
  layout("routes/layout.tsx", [
    route("projects", "routes/projects-dashboard.tsx"),
    route("projects/:projectId", "routes/project.tsx"),
  ]),
] satisfies RouteConfig;
