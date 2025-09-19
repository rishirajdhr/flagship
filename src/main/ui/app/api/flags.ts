import type { Auth, Flag } from "~/types";
import { withBase } from "./base";

export async function getAllFlagsForProject(projectId: string, auth: Auth) {
  const result = await fetch(withBase(`/api/projects/${projectId}/flags`), {
    headers: {
      Authorization: `Bearer ${auth.token}`,
    },
  });
  return result;
}

export interface FlagParams {
  flagId: string;
  projectId: string;
}

export interface UpdateFlagParams
  extends FlagParams,
    Partial<Pick<Flag, "description" | "enabled">> {}

export interface NewFlagParams
  extends Pick<Flag, "key" | "name" | "description" | "enabled"> {
  projectId: string;
}

export async function createFlagForProject(params: NewFlagParams, auth: Auth) {
  const { projectId, ...body } = params;
  const result = await fetch(withBase(`/api/projects/${projectId}/flags`), {
    method: "POST",
    headers: {
      Authorization: `Bearer ${auth.token}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(body),
  });
  return result;
}

export async function updateFlagForProject(
  params: UpdateFlagParams,
  auth: Auth
) {
  const { flagId, projectId, ...body } = params;
  const result = await fetch(
    withBase(`/api/projects/${projectId}/flags/${flagId}`),
    {
      method: "PUT",
      headers: {
        Authorization: `Bearer ${auth.token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(body),
    }
  );
  return result;
}

export async function deleteFlagForProject(params: FlagParams, auth: Auth) {
  const { flagId, projectId } = params;
  const result = await fetch(
    withBase(`/api/projects/${projectId}/flags/${flagId}`),
    {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${auth.token}`,
      },
    }
  );
  return result;
}
