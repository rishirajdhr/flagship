import type { Auth } from "~/types";
import { withBase } from "./base";

export async function getAllProjects(auth: Auth) {
  const result = await fetch(withBase(`/api/projects`), {
    headers: {
      Authorization: `Bearer ${auth.token}`,
    },
  });
  return result;
}

export interface NewProjectParams {
  name: string;
  description: string;
}

export async function createProject(params: NewProjectParams, auth: Auth) {
  const result = await fetch(withBase(`/api/projects`), {
    method: "POST",
    headers: {
      Authorization: `Bearer ${auth.token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(params),
  });
  return result;
}

export async function getProject(projectId: string, auth: Auth) {
  const result = await fetch(withBase(`/api/projects/${projectId}`), {
    headers: {
      Authorization: `Bearer ${auth.token}`,
    },
  });
  return result;
}
